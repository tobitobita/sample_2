package dsk.sampletransaction;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Relational Database
 *
 */
public class App {

    public static void main(String[] args) {
        Context ctx = new Context();
        //商品テーブル
        Table shohin = Table.create("shohin",
                new String[]{"shohin_id", "shohin_name", "kubun_id", "price"});
        ctx.into(shohin).insert(1, "りんご", 1, 300)
                .insert(2, "みかん", 1, 130)
                .insert(3, "キャベツ", 2, 200)
                .insert(4, "さんま", 3, 220)
                .insert(5, "わかめ", null, 250)//区分がnull
                .insert(6, "しいたけ", 4, 180)//該当区分なし
                .insert(7, "ドリアン", 1, null);
        //区分テーブル
        Table kubun = Table.create("kubun",
                new String[]{"kubun_id", "kubun_name"});
        ctx.into(kubun).insert(1, "くだもの")
                .insert(2, "野菜")
                .insert(3, "魚");
        //インデックス追加
        shohin.addIndex("shohin_id", IndexType.HASH);
        shohin.addIndex("price", IndexType.TREE);
        kubun.addIndex("kubun_id", IndexType.HASH);

        //System.out.println(shohin);//テーブル内容
        System.out.println(ctx.from("shohin"));//クエリー経由でテーブル内容
        System.out.println(ctx.from("shohin").select("shohin_name", "price"));//射影
        System.out.println(ctx.from("shohin").lessThan("price", 250));//フィルター
        System.out.println(ctx.from("shohin").leftJoin("kubun", "kubun_id"));//結合
        System.out.println(ctx.from("shohin").leftJoin("kubun", "kubun_id")//全部入り
                .lessThan("price", 200).select("shohin_name", "kubun_name", "price"));
        System.out.println(ctx //サブクエリー
                .from(ctx.from("shohin").lessThan("price", 250))
                .leftJoin(ctx.from("kubun").lessThan("kubun_id", 3), "kubun_id"));
        System.out.println(ctx //一致比較
                .from("shohin")
                .leftJoin("kubun", "kubun_id")
                .equalsTo("shohin_id", 2)
                .select("shohin_id", "shohin_name", "kubun_name", "price"));
        System.out.println(ctx //安い順に並べ替え
                .from("shohin")
                .orderBy("price"));
        System.out.println(ctx //高い順に並べ替え
                .from("shohin")
                .orderBy("price", false));
        System.out.println(ctx //集計
                .from(ctx
                        .from("shohin")
                        .groupBy("kubun_id", new Count("shohin_name"), new Average("price")))
                .leftJoin("kubun", "kubun_id")
                .select("kubun_id", "kubun_name", "count", "average"));

        System.out.println("between");
        System.out.println(ctx
                .from("shohin").between("price", 200, 250));
        System.out.println(ctx
                .from("shohin").between("price", 100, 150)
                .union(ctx
                        .from("shohin").between("price", 250, 300), true));
        System.out.println(ctx
                .from("shohin").between("price", 150, 200)
                .union(ctx
                        .from("shohin").between("price", 200, 250), true));
        System.out.println(ctx
                .from("shohin").between("price", 150, 200)
                .union(ctx
                        .from("shohin").between("price", 200, 250), false));

        //nullだったpriceに値を設定してみる
        ctx.from("shohin").equalsTo("shohin_id", 7).update(new SubstOperation("price", 150));
        System.out.println(ctx.from("shohin"));
        //ためしに抽出
        System.out.println(ctx.from("shohin").lessThan("price", 200));
        //値を変更
        ctx.from("shohin").equalsTo("shohin_id", 7).update(new SubstOperation("price", 80));
        System.out.println(ctx.from("shohin").lessThan("price", 200));
        //一括変更
        ctx.from("shohin").lessThan("price", 200).update(new AddOperation("price", 10));
        System.out.println(ctx.from("shohin").lessThan("price", 200));
        //値をnullに
        ctx.from("shohin").equalsTo("shohin_id", 2).update(new SubstOperation("price", null));
        System.out.println(ctx.from("shohin").lessThan("price", 200));
        //削除
        ctx.from("shohin").equalsTo("shohin_id", 7).delete();
        System.out.println(ctx.from("shohin").lessThan("price", 200));

        Context ctx2 = new Context();

        ctx.begin();
        ctx.into("shohin").insert(8, "レタス", 2, 250);

        //未コミットなのでctx2からは見えない
        System.out.println("no commit from ctx2\n" + ctx2.from("shohin").equalsTo("shohin_id", 8));
        //ctx1からは見える
        System.out.println("no commit from ctx\n" + ctx.from("shohin").equalsTo("shohin_id", 8));

        ctx2.begin();
        ctx.commit();
        //コミット前にトランザクションを開始したのでctx2からは見えない
        System.out.println("commit from ctx2\n" + ctx2.from("shohin").equalsTo("shohin_id", 8));
        ctx2.commit();
        //あとのトランザクションからは見える
        System.out.println("commit from other ctx2\n" + ctx2.from("shohin").equalsTo("shohin_id", 8));

        Context ctx3 = new Context();
        ctx3.begin();

        ctx.begin();
        ctx.from("shohin").equalsTo("shohin_id", 8).update(new SubstOperation("price", 200));
        //当該トランザクションからは変更が見える
        System.out.println("当該トランザクションからは変更が見える 200");
        System.out.println(ctx.from("shohin").equalsTo("shohin_id", 8));
        //他のトランザクションからは見えない
        System.out.println("他のトランザクションからは見えない 250");
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));
        //他のトランザクションで変更するとエラー
        try {
            ctx2.from("shohin").equalsTo("shohin_id", 8).update(new SubstOperation("price", 150));
            System.out.println("エラーが出るはず");
        } catch (IllegalStateException ex) {
            System.out.println("変更の衝突");
        }
        //他のトランザクションで削除するとエラー
        try {
            ctx2.from("shohin").equalsTo("shohin_id", 8).delete();
            System.out.println("エラーが出るはず");
        } catch (IllegalStateException ex) {
            System.out.println("削除の衝突");
        }
        //同一トランザクションでは変更できる
        ctx.from("shohin").equalsTo("shohin_id", 8).update(new SubstOperation("price", 180));
        System.out.println("同一トランザクションでは変更できる 180");
        System.out.println(ctx.from("shohin").equalsTo("shohin_id", 8));
        System.out.println("他トランザクションでは相変わらずもとの値 250");
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));

        //コミット前のトランザクションからは見えない
        ctx2.begin();
        ctx.commit();
        System.out.println("コミット前のトランザクションからは見えない 250");
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));
        ctx2.commit();
        //コミット後のトランザクションからは見える
        System.out.println("コミット後のトランザクションからは見える 180");
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));

        //コミット後なら変更できる
        ctx2.from("shohin").equalsTo("shohin_id", 8).update(new SubstOperation("price", 220));
        System.out.println("コミット後なら変更できる 220");
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));

        //ずっと前からのトランザクションからは変更されないまま
        System.out.println("ずっと前からのトランザクションからは変更されないまま 250");
        System.out.println(ctx3.from("shohin").equalsTo("shohin_id", 8));

        ctx.begin();
        //削除する
        ctx.from("shohin").equalsTo("shohin_id", 8).delete();
        System.out.println(ctx.from("shohin").equalsTo("shohin_id", 8));
        //他トランザクションからはまだ見える 220
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));
        //前のトランザクションからはふるい値が見える 250
        System.out.println(ctx3.from("shohin").equalsTo("shohin_id", 8));
        ctx3.commit();
        ctx.commit();
        //他のトランザクションからも見えない
        System.out.println(ctx2.from("shohin").equalsTo("shohin_id", 8));

    }

    /**
     * テーブル一覧
     */
    private static Map<String, Table> tables = new HashMap<>();

    /**
     * トランザクションID
     */
    static long currentTxid;

    public static class Transaction {

        /**
         * トランザクションID
         */
        long txid;

        List<TableTaple> inserted = new ArrayList<>();
        List<ModifiedTaple> modified = new ArrayList<>();

        public Transaction(long txid) {
            this.txid = txid;
        }

        public void commit(long curTx) {
            for (TableTaple tt : inserted) {
                tt.commitTx = curTx;
            }
            for (ModifiedTaple mt : modified) {
                mt.commit(curTx);
                if (mt instanceof UpdatedTaple) {
                    ((UpdatedTaple) mt).newTaple.commitTx = curTx;
                }
            }
        }

        public void abort() {

        }

        private void addInserted(TableTaple taple) {
            inserted.add(taple);
        }

        public boolean isAvailableTaple(TableTaple taple) {
            if (taple.isCommited()) {
                return taple.commitTx < txid;
            } else {
                return taple.createTx == txid;
            }
        }

        private void addModified(ModifiedTaple mt) {
            modified.add(mt);
        }
    }

    public static class Context {

        Transaction tx;

        public void begin() {
            if (hasTransaction()) {
                throw new IllegalStateException("already begin.");
            }
            ++currentTxid;
            tx = new Transaction(currentTxid);
        }

        public void commit() {
            tx.commit(currentTxid);
            tx = null;
        }

        public void abort() {
            tx.abort();
            tx = null;
        }

        public boolean hasTransaction() {
            return tx != null;
        }

        /**
         * クエリーのベースになるテーブルを選択
         *
         * @param tableName テーブル名
         * @return
         */
        public QueryObj from(String tableName) {
            return from(new QueryObj(this, tables.get(tableName)));
        }

        /**
         * クエリーのベースになるリレーションを選択
         *
         * @param tableName テーブル名
         * @return
         */
        public QueryObj from(QueryObj relation) {
            return relation;
        }

        public InsertObj into(String tableName) {
            return into(tables.get(tableName));
        }

        public InsertObj into(Table table) {
            return new InsertObj(this, table);
        }

    }

    public static class InsertObj {

        Context ctx;
        Table table;

        public InsertObj(Context ctx, Table table) {
            this.ctx = ctx;
            this.table = table;
        }

        public InsertObj insert(Object... values) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            table.insert(ctx.tx, values);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }
    }

    /**
     * クエリーとして利用できるメソッド(補完時に不要なメソッドを出さないため)
     */
    public static class QueryObj {

        Context ctx;
        AbstractRelation relation;

        public QueryObj(Context ctx, AbstractRelation relation) {
            this.ctx = ctx;
            this.relation = relation;
        }

        public AbstractRelation getRelation() {
            return relation;
        }

        /**
         * left join
         *
         * @param tableName 右側テーブル名
         * @param matchingField 値を結合する基準になる属性名
         * @return
         */
        public QueryObj leftJoin(String tableName, String matchingField) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.leftJoin(ctx.tx, tableName, matchingField);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        public QueryObj leftJoin(QueryObj rel, String matchingField) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.leftJoin(ctx.tx, rel.relation, matchingField);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 指定した値よりも小さいタプルを抽出
         *
         * @param columnName 比較するフィールド名
         * @param value 比較する値
         * @return
         */
        public QueryObj lessThan(String columnName, Integer value) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.lessThan(ctx.tx, columnName, value);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 指定した値の間のタプルを抽出
         *
         * @param columnName 比較するフィールド名
         * @param lower 抽出範囲の下側
         * @param upper 抽出範囲の上側
         * @return
         */
        public QueryObj between(String columnName, Integer lower, Integer upper) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.between(ctx.tx, columnName, lower, upper);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 指定した値に一致するタプルを抽出
         *
         * @param columnName 比較するフィールド名
         * @param value 比較する値
         * @return
         */
        public QueryObj equalsTo(String columnName, Object value) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.equalsTo(ctx.tx, columnName, value);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 射影
         *
         * @param columnNames 抽出するフィールド名
         * @return
         */
        public QueryObj select(String... columnNames) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.select(ctx.tx, columnNames);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 昇順に並べ替え
         *
         * @param columnName 並べ替えるフィールド名
         * @return
         */
        public QueryObj orderBy(String columnName) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.orderBy(ctx.tx, columnName);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 並べ替え
         *
         * @param columnName 並べ替えるフィールド名
         * @param asc 昇順のばあいtrue
         * @return
         */
        public QueryObj orderBy(String columnName, boolean asc) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.orderBy(ctx.tx, columnName, asc);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * グループ化
         *
         * @param columnName グループ化するフィールド名
         * @param aggregations 集計関数
         * @return
         */
        public QueryObj groupBy(String columnName, Aggregation... aggregations) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.groupBy(ctx.tx, columnName, aggregations);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        /**
         * 変更
         *
         * @param op 変更するフィールド
         */
        public void update(UpdateOperation... op) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            try {
                relation.update(ctx.tx, op);
                if (!hasTx) {
                    ctx.commit();
                }
            } catch (Exception ex) {
                ctx.abort();
                throw ex;
            }
        }

        /**
         * 削除
         */
        public void delete() {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            try {
                relation.delete(ctx.tx);
                if (!hasTx) {
                    ctx.commit();
                }
            } catch (Exception ex) {
                ctx.abort();
                throw ex;
            }
        }

        /**
         * 連結 行の数や名前、順番が一致しない場合は連結しない
         *
         * @param relation 連結対象
         * @param distinct テーブル同士の連結の場合、重複を省く
         */
        public QueryObj union(QueryObj rel, boolean distinct) {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            relation = relation.union(ctx.tx, rel.relation, distinct);
            if (!hasTx) {
                ctx.commit();
            }
            return this;
        }

        public int count() {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            int count = relation.count(ctx.tx);
            if (!hasTx) {
                ctx.commit();
            }
            return count;
        }

        @Override
        public String toString() {
            boolean hasTx = ctx.hasTransaction();
            if (!hasTx) {
                ctx.begin();
            }
            String result = relation.toString(ctx.tx);
            if (!hasTx) {
                ctx.commit();
            }
            return result;
        }
    }

    /**
     * 集計関数の基底
     */
    public static abstract class Aggregation {

        String columnName;

        public Aggregation(String columnName) {
            this.columnName = columnName;
        }

        /**
         * 関数名
         */
        public abstract String getName();

        /**
         * データ追加
         */
        public abstract void addData(Object value);

        /**
         * 結果取得
         */
        public abstract Object getResult();

        /**
         * リセット
         */
        public abstract void reset();
    }

    /**
     * 集計用カウント関数
     */
    public static class Count extends Aggregation {

        int counter = 0;

        @Override
        public String getName() {
            return "count";
        }

        public Count(String columnName) {
            super(columnName);
        }

        @Override
        public void addData(Object value) {
            ++counter;
        }

        @Override
        public Object getResult() {
            return counter;
        }

        @Override
        public void reset() {
            counter = 0;
        }
    }

    /**
     * 集計用平均関数
     */
    public static class Average extends Aggregation {

        int counter = 0;
        int total = 0;

        public Average(String columnName) {
            super(columnName);
        }

        @Override
        public String getName() {
            return "average";
        }

        @Override
        public void addData(Object value) {
            ++counter;
            total += (Integer) value;
        }

        @Override
        public Object getResult() {
            return (double) total / counter;
        }

        @Override
        public void reset() {
            counter = 0;
            total = 0;
        }
    }

    /**
     * リレーション
     */
    public static abstract class AbstractRelation {

        public abstract List<Column> getColumns();

        public abstract List<Taple> getTaples(Transaction tx);

        /**
         * 射影
         */
        public AbstractRelation select(Transaction tx, String... columnNames) {
            List<Integer> indexes = new ArrayList<>();
            List<Column> newColumns = new ArrayList<>();
            for (String n : columnNames) {
                newColumns.add(new Column(n));
                //属性を探す
                int idx = findColumn(n);
                indexes.add(idx);
            }
            //データの投影
            List<Taple> newTaples = new ArrayList<>();
            for (Taple tp : getTaples(tx)) {
                List<Object> values = new ArrayList<>();
                for (int idx : indexes) {
                    if (idx < tp.values.size()) {
                        values.add(tp.values.get(idx));
                    } else {
                        values.add(null);
                    }
                }
                newTaples.add(new Taple(values));
            }

            return new Relation(newColumns, newTaples);
        }

        /**
         * left join
         */
        public AbstractRelation leftJoin(Transaction tx, String tableName, String matchingField) {
            //テーブル取得
            Table tbl = tables.get(tableName);
            return leftJoin(tx, tbl, matchingField);
        }

        public AbstractRelation leftJoin(Transaction tx, AbstractRelation relation, String matchingField) {
            Relation tbl = (Relation) relation;
            //属性の作成
            List<Column> newColumns = new ArrayList<>(getColumns());
            newColumns.addAll(tbl.getColumns());

            //値の作成
            List<Taple> newTaples = new ArrayList<>();
            int leftColumnIdx = findColumn(matchingField);
            int rightColumnIdx = tbl.findColumn(matchingField);
            if (leftColumnIdx >= getColumns().size() || rightColumnIdx >= tbl.getColumns().size()) {
                //該当フィールドがない場合は値の結合をしない
                return new Relation(newColumns, Collections.EMPTY_LIST);
            }
            //結合処理
            for (Taple tp : getTaples(tx)) {
                //元のテーブルのデータ
                Taple ntpl = new Taple(new ArrayList<>(tp.values));
                //足りないフィールドを埋める
                while (ntpl.values.size() < getColumns().size()) {
                    ntpl.values.add(null);
                }
                //結合対象のフィールドを探す
                Object leftValue = ntpl.values.get(leftColumnIdx);
                //結合するタプルを抽出
                Relation leftRel = (Relation) relation.equalsTo(tx, matchingField, leftValue);
                //一致するタプルがあれば結合
                if (!leftRel.taples.isEmpty()) {
                    for (Object v : leftRel.getTaples(tx).get(0).values) {//今回は、タプルの対応は一対一まで
                        ntpl.values.add(v);
                    }
                }

                //足りないフィールドを埋める
                while (ntpl.values.size() < newColumns.size()) {
                    ntpl.values.add(null);
                }

                newTaples.add(ntpl);
            }
            return new Relation(newColumns, newTaples);
        }

        /**
         * 指定した値よりも小さいタプルを抽出
         */
        public AbstractRelation lessThan(Transaction tx, String columnName, Integer value) {
            if (value == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            List<Taple> newTaples = new ArrayList<>();
            for (Taple tp : getTaples(tx)) {
                try {
                    if ((Integer) tp.values.get(idx) < value) {
                        newTaples.add(tp);
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                }
            }
            return new Relation(getColumns(), newTaples);
        }

        /**
         * 指定した値に一致するタプルを抽出
         */
        public AbstractRelation equalsTo(Transaction tx, String columnName, Object value) {
            if (value == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            List<Taple> newTaples = new ArrayList<>();
            for (Taple tp : getTaples(tx)) {
                if (value.equals(tp.values.get(idx))) {
                    newTaples.add(tp);
                }
            }
            return new Relation(getColumns(), newTaples);
        }

        public AbstractRelation between(Transaction tx, String columnName, Integer lower, Integer upper) {
            if (lower == null || upper == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            List<Taple> newTaples = new ArrayList<>();
            for (Taple tp : getTaples(tx)) {
                try {
                    int v = (Integer) tp.values.get(idx);
                    if (lower <= v && v <= upper) {
                        newTaples.add(tp);
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                }
            }
            return new Relation(getColumns(), newTaples);
        }

        public AbstractRelation orderBy(Transaction tx, String columnName) {
            return orderBy(tx, columnName, true);
        }

        public AbstractRelation orderBy(Transaction tx, String columnName, final boolean asc) {
            final int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return this;
            }

            List<Taple> newTaple = new ArrayList<>(getTaples(tx));
            Collections.sort(newTaple, new Comparator<Taple>() {
                @Override
                public int compare(Taple o1, Taple o2) {
                    Object v1 = o1.values.size() > idx ? o1.values.get(idx) : null;
                    Object v2 = o2.values.size() > idx ? o2.values.get(idx) : null;
                    if (v1 == null) {
                        if (v2 == null) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                    if (v2 == null) {
                        return -1;
                    }
                    return ((Comparable) v1).compareTo(v2) * (asc ? 1 : -1);
                }
            });

            return new Relation(getColumns(), newTaple);
        }

        public AbstractRelation groupBy(Transaction tx, String columnName, Aggregation... aggregations) {
            //列名を作成
            List<Column> newColumns = new ArrayList<>();
            newColumns.add(new Column(columnName));
            List<Integer> colIndexes = new ArrayList<>();
            for (Aggregation agg : aggregations) {
                newColumns.add(new Column(agg.getName()));
                colIndexes.add(findColumn(agg.columnName));
            }

            //集計行を取得
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(newColumns, Collections.EMPTY_LIST);
            }

            //あらかじめソート
            Relation sorted = (Relation) orderBy(tx, columnName);

            Object current = null;
            List<Taple> newTaples = new ArrayList<>();
            for (Taple tp : sorted.getTaples(tx)) {
                //集計フィールド取得
                if (tp.values.size() <= idx) {
                    continue;
                }
                Object v = tp.values.get(idx);
                if (v == null) {
                    continue;
                }

                if (!v.equals(current)) {
                    if (current != null) {
                        //集計行を追加
                        List<Object> values = new ArrayList<>();
                        values.add(current);
                        for (Aggregation agg : aggregations) {
                            values.add(agg.getResult());
                        }
                        newTaples.add(new Taple(values));
                    }
                    current = v;
                    for (Aggregation agg : aggregations) {
                        agg.reset();
                    }
                }
                //集計
                for (int i = 0; i < aggregations.length; ++i) {
                    int aidx = colIndexes.get(i);
                    if (tp.values.size() <= aidx) {
                        continue;
                    }
                    Object cv = tp.values.get(aidx);
                    if (cv == null) {
                        continue;
                    }

                    Aggregation ag = aggregations[i];
                    ag.addData(cv);
                }
            }
            if (current != null) {
                //集計行を追加
                List<Object> values = new ArrayList<>();
                values.add(current);
                for (Aggregation agg : aggregations) {
                    values.add(agg.getResult());
                }
                newTaples.add(new Taple(values));
            }

            return new Relation(newColumns, newTaples);
        }

        /**
         * データ更新
         *
         * @param op 更新
         */
        public void update(Transaction tx, UpdateOperation... op) {
            ArrayList<Taple> iterateTaples = new ArrayList<>(getTaples(tx));//ループ中で要素を削除する可能性があるため
            for (Taple tp : iterateTaples) {
                if (tp instanceof TableTaple) {
                    TableTaple ttp = (TableTaple) tp;
                    ttp.parent.execUpdate(tx, ttp, op);
                }
            }
        }

        /**
         * データ削除
         */
        public void delete(Transaction tx) {
            ArrayList<Taple> iterateTaples = new ArrayList<>(getTaples(tx));//ループ中で要素を削除する可能性があるため
            for (Taple tp : iterateTaples) {
                if (tp instanceof TableTaple) {
                    TableTaple ttp = (TableTaple) tp;
                    ttp.parent.execDelete(tx, ttp);
                }
            }
        }

        public AbstractRelation union(Transaction tx, AbstractRelation relation, boolean distinct) {
            AbstractRelation rel = (AbstractRelation) relation;
            //カラムの比較
            if (getColumns().size() != rel.getColumns().size()) {
                //カラム数が違う
                return this;
            }
            for (int i = 0; i < getColumns().size(); ++i) {
                if (!getColumns().get(i).name.equals(rel.getColumns().get(i).name)) {
                    //名前が違う
                    return this;
                }
            }

            List<Taple> result = new ArrayList<>();
            Set<Long> oids = new HashSet<>();
            //元データの追加
            for (Taple tp : getTaples(tx)) {
                if (distinct && tp instanceof TableTaple) {
                    //重複チェック
                    long oid = ((TableTaple) tp).oid;
                    if (oids.contains(oid)) {
                        continue;
                    }
                    oids.add(oid);
                }
                result.add(tp);
            }
            //unionデータの追加
            for (Taple tp : rel.getTaples(tx)) {
                if (distinct && tp instanceof TableTaple) {
                    //重複チェック
                    long oid = ((TableTaple) tp).oid;
                    if (oids.contains(oid)) {
                        continue;
                    }
                    oids.add(oid);
                }
                result.add(tp);
            }

            return new Relation(getColumns(), result);
        }

        public int count(Transaction tx) {
            return getTaples(tx).size();
        }

        /**
         * 属性を探す
         *
         * @param name 属性名
         * @return 属性のインデックス。みつからなかったときは属性数(インデックスからあふれる)を返す
         */
        public int findColumn(String name) {
            for (int i = 0; i < getColumns().size(); ++i) {
                if (getColumns().get(i).name.equals(name)) {
                    return i;
                }
            }
            return getColumns().size();
        }

        public String toString(Transaction tx) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            //フィールド名
            for (Column cl : getColumns()) {
                pw.print("|");
                if (cl.parent != null && !cl.parent.isEmpty()) {
                    pw.print(cl.parent + ".");
                }
                pw.print(cl.name);
            }
            pw.println("|");
            //データ
            int c = 0;
            for (Taple t : getTaples(tx)) {
                for (Object v : t.values) {
                    pw.print("|");
                    pw.print(v);
                }
                pw.println("|");
                ++c;
                if (c > 10) {
                    break;
                }
            }

            return sw.toString();
        }
    }

    /**
     * アップデート用演算
     */
    public static abstract class UpdateOperation {

        String tapleName;
        Object value;

        public UpdateOperation(String tapleName, Object value) {
            this.tapleName = tapleName;
            this.value = value;
        }

        public abstract Object eval(Object oldValue);
    }

    /**
     * 代入
     */
    public static class SubstOperation extends UpdateOperation {

        public SubstOperation(String tapleName, Object value) {
            super(tapleName, value);
        }

        @Override
        public Object eval(Object oldValue) {
            return value;
        }
    }

    /**
     * 足し算
     */
    public static class AddOperation extends UpdateOperation {

        public AddOperation(String tapleName, int value) {
            super(tapleName, value);
        }

        @Override
        public Object eval(Object oldValue) {
            return (Integer) oldValue + (Integer) value;
        }
    }

    public static class Relation extends AbstractRelation {

        /**
         * 属性
         */
        private List<Column> columns;
        /**
         * データ
         */
        protected List<Taple> taples;

        public Relation(List<Column> columns, List<Taple> taples) {
            this.columns = columns;
            this.taples = taples;
        }

        @Override
        public List<Column> getColumns() {
            return columns;
        }

        @Override
        public List<Taple> getTaples(Transaction tx) {
            return taples;
        }
    }

    /**
     * 現在のオブジェクトID
     */
    static long currentOid = 0;

    public static abstract class ModifiedTaple {

        TableTaple oldTaple;
        long modifyTx;
        long commitedTx;

        public ModifiedTaple(long modifyTx, TableTaple oldTaple) {
            this.oldTaple = oldTaple;
            this.modifyTx = modifyTx;
        }

        public boolean isCommited() {
            return commitedTx != 0;
        }

        public void commit(long curTxid) {
            commitedTx = curTxid;
        }
    }

    public static class UpdatedTaple extends ModifiedTaple {

        TableTaple newTaple;

        public UpdatedTaple(long modifyTx, TableTaple oldTaple, TableTaple newTaple) {
            super(modifyTx, oldTaple);
            this.newTaple = newTaple;
        }
    }

    public static class DeletedTaple extends ModifiedTaple {

        public DeletedTaple(long modifyTx, TableTaple oldTaple) {
            super(modifyTx, oldTaple);
        }
    }

    /**
     * テーブル
     */
    public static class Table extends Relation {

        /**
         * テーブル名
         */
        String name;

        /**
         * インデックス
         */
        List<Index> indexes = new ArrayList<>();

        //キーはoid
        Map<Long, List<ModifiedTaple>> modifiedTaples = new HashMap<>();

        /**
         * テーブル生成
         */
        public static Table create(String name, String[] columnNames) {
            List<Column> columns = new ArrayList<>();
            for (String n : columnNames) {
                columns.add(new TableColumn(name, n));
            }
            Table t = new Table(name, columns);
            tables.put(name, t);
            return t;
        }

        /**
         * テーブルオブジェクトの生成
         *
         * @param name テーブル名
         * @param columns 属性
         */
        private Table(String name, List<Column> columns) {
            super(columns, new ArrayList<Taple>());
            this.name = name;
        }

        public List<Taple> getModifiedTaples(Transaction tx) {
            List<Taple> result = new ArrayList<>();
            for (List<ModifiedTaple> list : modifiedTaples.values()) {
                //一番新しいものを使うので逆順に走査
                for (Iterator<ModifiedTaple> di = ((LinkedList<ModifiedTaple>) list).descendingIterator();
                        di.hasNext();) {
                    ModifiedTaple mt = di.next();
                    if (mt.modifyTx == tx.txid) {
                        //同じトランザクションで変更されているなら、変更履歴のデータを使わない
                        break;
                    }
                    if (mt.isCommited() && mt.commitedTx < tx.txid) {
                        //すでにトランザクション前にコミットされているなら、変更履歴のデータを使わない
                        break;
                    }
                    if (tx.isAvailableTaple(mt.oldTaple)) {
                        result.add(mt.oldTaple);
                        break;
                    }
                }
            }
            return result;
        }

        @Override
        public List<Taple> getTaples(Transaction tx) {
            List<Taple> result = new ArrayList<>();
            for (Taple tp : taples) {
                TableTaple ttp = (TableTaple) tp;
                if (tx.isAvailableTaple(ttp)) {
                    //トランザクション開始前にコミット済か、同じトランザクションで生成されたものだけ追加
                    result.add(ttp);
                }
            }
            //別トランザクションで変更されたものを追加
            result.addAll(getModifiedTaples(tx));
            return result;
        }

        @Override
        public AbstractRelation lessThan(Transaction tx, String columnName, Integer value) {
            if (value == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            TableColumn column = (TableColumn) getColumns().get(idx);
            if (column.treeIndex != null) {
                //ツリーインデックスがあるときはツリーインデックスを使う
                return column.treeIndex.lessThan(tx, columnName, value);
            }
            return super.lessThan(tx, columnName, value);
        }

        @Override
        public AbstractRelation equalsTo(Transaction tx, String columnName, Object value) {
            if (value == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            TableColumn column = (TableColumn) getColumns().get(idx);
            if (column.hashIndex != null) {
                //ハッシュインデックスがあるならハッシュインデックス
                return column.hashIndex.equalsTo(tx, columnName, value);
            } else if (column.treeIndex != null) {
                //ツリーインデックスがあるならツリーインデックス
                return column.treeIndex.equalsTo(tx, columnName, value);
            }
            return super.equalsTo(tx, columnName, value);
        }

        @Override
        public AbstractRelation between(Transaction tx, String columnName, Integer lower, Integer upper) {
            if (lower == null || upper == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            TableColumn column = (TableColumn) getColumns().get(idx);
            if (column.treeIndex != null) {
                //ツリーインデックスがあるときはツリーインデックスを使う
                return column.treeIndex.between(tx, columnName, lower, upper);
            }
            return super.between(tx, columnName, lower, upper);
        }

        @Override
        public AbstractRelation orderBy(Transaction tx, String columnName, boolean asc) {
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return this;
            }
            TableColumn column = (TableColumn) getColumns().get(idx);
            if (column.treeIndex != null) {
                //ツリーインデックスがあるときはツリーインデックスを使う
                return column.treeIndex.orderBy(tx, columnName, asc);
            }
            return super.orderBy(tx, columnName, asc);
        }

        @Override
        public AbstractRelation groupBy(Transaction tx, String columnName, Aggregation... aggregations) {
            int idx = findColumn(columnName);
            if (idx >= getColumns().size()) {
                return super.groupBy(tx, columnName, aggregations);
            }
            TableColumn column = (TableColumn) getColumns().get(idx);
            Index index = column.treeIndex;
            if (index == null) {
                index = column.hashIndex;
            }
            if (index != null) {
                //インデックスがあるならインデックスを使って集計
                return index.groupBy(tx, columnName, aggregations);
            }
            return super.groupBy(tx, columnName, aggregations);
        }

        /**
         * タプルの追加
         *
         * @param values 値
         * @return
         */
        public Table insert(Transaction tx, Object... values) {
            ++currentOid;
            TableTaple taple = new TableTaple(tx, currentOid, this, Arrays.asList(values));
            taples.add(taple);
            for (Index index : indexes) {
                index.insert(taple);
            }
            tx.addInserted(taple);
            return this;
        }

        /**
         * インデックス追加
         *
         * @param columnName フィールド名
         * @param type インデックス種別
         */
        public Index addIndex(String columnName, IndexType type) {
            int idx = findColumn(columnName);
            TableColumn col = (TableColumn) getColumns().get(idx);
            Index index;
            if (type == IndexType.TREE) {
                if (col.treeIndex == null) {
                    index = new TreeIndex(this, col, idx);
                    col.treeIndex = (TreeIndex) index;
                } else {
                    return col.treeIndex;
                }
            } else if (type == IndexType.HASH) {
                if (col.hashIndex == null) {
                    index = new HashIndex(this, col, idx);
                    col.hashIndex = (HashIndex) index;
                } else {
                    return col.hashIndex;
                }
            } else {
                return null;
            }
            indexes.add(index);
            //すでに存在するデータをインデックスに追加
            for (Taple tp : taples) {
                index.insert(tp);
            }
            return index;
        }

        private void execDelete(Transaction tx, TableTaple tp) {
            if (tp.modified) {
                throw new IllegalStateException("update conflict");
            }
            taples.remove(tp);
            for (Index index : indexes) {
                index.delete(tp);
            }
            //変更履歴を保存
            if (tp.createTx == tx.txid) {
                //自分のトランザクションで変更したデータは履歴をとらない
                return;
            }
            tp.modified = true;
            DeletedTaple dt = new DeletedTaple(tx.txid, tp);
            addModifiedTaple(dt);
            tx.addModified(dt);
        }

        private void execUpdate(Transaction tx, TableTaple tp, UpdateOperation[] op) {
            if (op.length == 0) {
                return;
            }
            if (tp.modified) {
                throw new IllegalStateException("update conflict");
            }
            List<UpdatedField> updated = new ArrayList<>();
            //古いタプルの情報を保存
            TableTaple oldTaple = new TableTaple(tx, tp.oid, this, tp.values);
            oldTaple.commitTx = tp.commitTx;
            oldTaple.createTx = tp.createTx;
            oldTaple.modified = true;

            for (UpdateOperation uo : op) {
                int idx = findColumn(uo.tapleName);
                if (idx >= getColumns().size()) {
                    continue;
                }
                Object oldValue = tp.values.size() <= idx ? null : tp.values.get(idx);
                Object newValue = uo.eval(oldValue);
                if ((oldValue == null && newValue != null) || (oldValue != null && !oldValue.equals(newValue))) {
                    //値がかわった
                    //値を埋める
                    while (tp.values.size() <= idx) {
                        tp.values.add(null);
                    }
                    //値の変更
                    tp.values.set(idx, newValue);
                    //変更の記録
                    updated.add(new UpdatedField(uo.tapleName, idx, oldValue, newValue));
                }
                //同じフィールドに対する変更のチェックは行っていない
            }
            if (updated.isEmpty()) {
                //変更がない
                return;
            }

            //インデックスに反映
            for (Index index : indexes) {
                index.update(tp, updated);
            }
            if (tp.createTx == tx.txid) {
                //自分のトランザクションで変更したデータは履歴をとらない
                return;
            }
            tp.commitTx = 0;
            tp.createTx = tx.txid;
            //変更履歴を保存
            UpdatedTaple ut = new UpdatedTaple(tx.txid, oldTaple, tp);
            addModifiedTaple(ut);
            tx.addModified(ut);
        }

        private void addModifiedTaple(ModifiedTaple mt) {
            long oid = mt.oldTaple.oid;
            List<ModifiedTaple> list = modifiedTaples.get(oid);
            if (list == null) {
                list = new LinkedList<>();
                modifiedTaples.put(oid, list);
            }
            list.add(mt);
        }

        void removeModifiedTaple(ModifiedTaple mt) {
            long oid = mt.oldTaple.oid;
            List<ModifiedTaple> list = modifiedTaples.get(oid);
            if (list == null) {
                return;
            }
            list.remove(mt);
            if (list.isEmpty()) {
                modifiedTaples.remove(oid);
            }
        }
    }

    /**
     * タプル
     */
    public static class Taple {

        /**
         * 保持する値
         */
        List<Object> values;

        public Taple(List<Object> values) {
            this.values = new ArrayList(values);
        }

    }

    /**
     * テーブルのタプル
     */
    public static class TableTaple extends Taple {

        Table parent;
        long oid;
        long createTx;
        long commitTx;
        boolean modified;

        public TableTaple(Transaction tx, long oid, Table parent, List<Object> values) {
            super(values);
            this.oid = oid;
            this.parent = parent;
            this.createTx = tx.txid;
        }

        void commit(long curTxid) {
            commitTx = curTxid;
        }

        boolean isCommited() {
            return commitTx != 0;
        }
    }

    /**
     * 属性
     */
    public static class Column {

        /**
         * テーブルなどの名前
         */
        String parent;
        /**
         * 属性名
         */
        String name;

        public Column(String parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        public Column(String name) {
            this("", name);
        }
    }

    /**
     * テーブルのカラム定義
     */
    public static class TableColumn extends Column {

        HashIndex hashIndex;
        TreeIndex treeIndex;

        public TableColumn(String parent, String name) {
            super(parent, name);
        }

    }

    /**
     * インデックス種別
     */
    public static enum IndexType {

        TREE,
        HASH
    }

    /**
     * インデックス
     */
    public static abstract class Index extends AbstractRelation {

        Relation parent;
        Column belongs;
        int colIndex;

        public Index(Relation parent, Column belongs, int colIndex) {
            this.parent = parent;
            this.belongs = belongs;
            this.colIndex = colIndex;
        }

        @Override
        public List<Column> getColumns() {
            return parent.getColumns();
        }

        public abstract void insert(Taple taple);

        public abstract void delete(Taple taple);

        public abstract void update(Taple newtaple, List<UpdatedField> updated);
    }

    public static class UpdatedField {

        String columnName;
        int columnIndex;
        Object oldValue;
        Object newValue;

        public UpdatedField(String columnName, int columnIndex, Object oldValue, Object newValue) {
            this.columnName = columnName;
            this.columnIndex = columnIndex;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
    }

    public static abstract class MapIndex extends Index {

        Map<Object, List<Taple>> indexed;

        public MapIndex(Relation parent, Column belongs, int colIndex, Map<Object, List<Taple>> indexed) {
            super(parent, belongs, colIndex);
            this.indexed = indexed;
        }

        @Override
        public void insert(Taple taple) {
            Object value = taple.values.get(colIndex);
            if (value == null) {
                //null値にはインデックスはない
                return;
            }
            List<Taple> taples = indexed.get(value);
            if (taples == null) {
                taples = new ArrayList<>();
                indexed.put(value, taples);
            }
            taples.add(taple);
        }

        @Override
        public void delete(Taple taple) {
            //インデックス対象フィールドの確認
            if (taple.values.size() <= colIndex) {
                return;
            }
            Object value = taple.values.get(colIndex);
            removeValue(value, taple);
        }

        @Override
        public void update(Taple newtaple, List<UpdatedField> updated) {
            for (UpdatedField uf : updated) {
                if (uf.columnIndex == colIndex) {
                    //インデックス対象行の変更の場合
                    removeValue(uf.oldValue, newtaple);
                    insert(newtaple);
                }
            }
        }

        /**
         * 値にひもづけられた行を削除
         */
        private void removeValue(Object value, Taple taple) {
            if (value == null) {
                return;
            }
            //インデックスに格納されている行を取得
            List<Taple> list = indexed.get(value);
            if (list == null) {
                System.out.println("なぜか" + value + "がインデックスにない");
                return;
            }
            //インデックスから削除
            list.remove(taple);
            if (list.isEmpty()) {
                indexed.remove(value);
            }
        }

        @Override
        public List<Taple> getTaples(Transaction tx) {
            List<Taple> result = flatten(tx, indexed.values());
            if (parent instanceof Table) {
                result.addAll(((Table) parent).getModifiedTaples(tx));
            }
            return result;
        }

        @Override
        public AbstractRelation equalsTo(Transaction tx, String columnName, Object value) {
            List<Taple> taples = indexed.get(value);
            if (value == null) {
                return new Relation(getColumns(), Collections.EMPTY_LIST);
            }
            List<Taple> result = new ArrayList<>();
            if (taples != null) {
                for (Taple tp : taples) {
                    if (!(tp instanceof TableTaple) || tx.isAvailableTaple((TableTaple) tp)) {
                        result.add(tp);
                    }
                }
            }
            if (parent instanceof Table) {
                //変更履歴からデータを取ってくる
                List<Taple> modifiedTaples = ((Table) parent).getModifiedTaples(tx);
                int idx = findColumn(columnName);
                for (Taple tp : modifiedTaples) {
                    if (tp.values.size() > idx && value.equals(tp.values.get(idx))) {
                        result.add(tp);
                    }
                }
            }
            return new Relation(getColumns(), result);
        }

        @Override
        public AbstractRelation groupBy(Transaction tx, String columnName, Aggregation... aggregations) {
            //変更履歴に対応していない
            //カラム作成
            List<Column> newColumns = new ArrayList<>();
            newColumns.add(new Column(columnName));
            List<Integer> colIndexes = new ArrayList<>();
            for (Aggregation agg : aggregations) {
                newColumns.add(new Column(agg.getName()));
                colIndexes.add(findColumn(agg.columnName));
            }

            //集計
            List<Taple> taples = new ArrayList<>();
            for (Map.Entry<Object, List<Taple>> me : indexed.entrySet()) {
                List<Object> values = new ArrayList<>();
                //集計対象行
                values.add(me.getKey());
                //一旦集計をリセット
                for (Aggregation agg : aggregations) {
                    agg.reset();
                }
                //集計
                for (Taple tp : me.getValue()) {
                    if (tp instanceof TableTaple) {
                        if (!tx.isAvailableTaple((TableTaple) tp)) {
                            continue;
                        }
                    }
                    for (int i = 0; i < colIndexes.size(); ++i) {
                        int idx = colIndexes.get(i);
                        if (idx >= tp.values.size()) {
                            continue;
                        }
                        Object value = tp.values.get(idx);
                        if (value == null) {
                            continue;
                        }
                        Aggregation ag = aggregations[i];
                        ag.addData(value);
                    }
                }
                //結果の格納
                for (Aggregation agg : aggregations) {
                    values.add(agg.getResult());
                }
                taples.add(new Taple(values));
            }
            return new Relation(newColumns, taples);
        }

        /**
         * リストのリストを平坦にする
         *
         * @param taples
         * @return
         */
        static List<Taple> flatten(Collection<List<Taple>> taples) {
            List<Taple> result = new ArrayList<>();
            for (List<Taple> vl : taples) {
                for (Taple tp : vl) {
                    result.add(tp);
                }
            }
            return result;
        }

        static List<Taple> flatten(Transaction tx, Collection<List<Taple>> taples) {
            List<Taple> result = new ArrayList<>();
            for (List<Taple> vl : taples) {
                for (Taple tp : vl) {
                    if (!(tp instanceof TableTaple) || tx.isAvailableTaple((TableTaple) tp)) {
                        result.add(tp);
                    }
                }
            }
            return result;
        }

    }

    /**
     * ツリーインデックス
     */
    public static class TreeIndex extends MapIndex {

        public TreeIndex(Relation parent, Column belongs, int colIndex) {
            super(parent, belongs, colIndex, new TreeMap<Object, List<Taple>>());
        }

        @Override
        public AbstractRelation lessThan(Transaction tx, String columnName, Integer value) {
            TreeMap<Object, List<Taple>> tindexed = (TreeMap<Object, List<Taple>>) indexed;
            NavigableMap<Object, List<Taple>> headMap = tindexed.headMap(value, false);
            List<Taple> result = flatten(tx, headMap.values());
            if (parent instanceof Table) {
                //変更履歴からデータを取ってくる
                List<Taple> modifiedTaples = ((Table) parent).getModifiedTaples(tx);
                int idx = findColumn(columnName);
                for (Taple tp : modifiedTaples) {
                    try {
                        if ((Integer) tp.values.get(idx) < value) {
                            result.add(tp);
                        }
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                    }
                }
            }

            return new Relation(getColumns(), result);
        }

        @Override
        public AbstractRelation between(Transaction tx, String columnName, Integer lower, Integer upper) {
            TreeMap<Object, List<Taple>> tindexed = (TreeMap<Object, List<Taple>>) indexed;
            NavigableMap<Object, List<Taple>> betweenMap = tindexed.subMap(lower, true, upper, true);
            List<Taple> result = flatten(tx, betweenMap.values());
            if (parent instanceof Table) {
                //変更履歴からデータを取ってくる
                List<Taple> modifiedTaples = ((Table) parent).getModifiedTaples(tx);
                int idx = findColumn(columnName);
                for (Taple tp : modifiedTaples) {
                    try {
                        Integer i = (Integer) tp.values.get(idx);
                        if (lower <= i && i <= upper) {
                            result.add(tp);
                        }
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                    }
                }
            }
            return new Relation(getColumns(), result);
        }

        @Override
        public AbstractRelation orderBy(Transaction tx, String columnName, boolean asc) {
            //変更履歴に対応していない
            Collection<List<Taple>> values;
            if (asc) {
                values = indexed.values();
            } else {
                TreeMap<Object, List<Taple>> tindexed = (TreeMap<Object, List<Taple>>) indexed;
                values = tindexed.descendingMap().values();
            }
            return new Relation(getColumns(), flatten(tx, values));
        }

    }

    /**
     * ハッシュインデックス
     */
    public static class HashIndex extends MapIndex {

        public HashIndex(Relation parent, Column belongs, int colIndex) {
            super(parent, belongs, colIndex, new LinkedHashMap<Object, List<Taple>>());
        }

    }
}
