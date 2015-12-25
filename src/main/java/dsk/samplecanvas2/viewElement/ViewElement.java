package dsk.samplecanvas2.viewElement;

import java.util.List;
import java.util.Optional;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * ViewElementを表すインターフェイス。
 */
public interface ViewElement {

	/**
	 * 次の兄弟を取得する。
	 *
	 * @return 次の兄弟。
	 */
	default Optional<ViewElement> getNextSiblingViewElement() {
		List<Node> list = this.getParent().getChildrenUnmodifiable();
		final int index = list.indexOf(this);
		if (index - 1 >= 0) {
			return Optional.of((ViewElement) list.get(index - 1));
		}
		return Optional.empty();
	}

	/**
	 * ViewElementの余白を取得する。
	 *
	 * @return 余白。
	 */
	default double getViewElementPadding() {
		return 15d;
	}

	/**
	 * ViewElementの推奨幅を設定する。
	 *
	 * @param prefWidth 推奨幅。
	 */
	default void setViewElementPrefWidth(double prefWidth) {
		this.setPrefWidth(prefWidth + getViewElementPadding() * 2);
	}

	/**
	 * ViewElementの推奨高さを設定する。
	 *
	 * @param prefHeight 推奨高さ。
	 */
	default void setViewElementPrefHeight(double prefHeight) {
		this.setPrefHeight(prefHeight + getViewElementPadding() * 2);
	}

	/**
	 * ViewElementのレイアウトXを取得する。
	 *
	 * @return レイアウトX。
	 */
	default double getViewElementLayoutX() {
		return this.getLayoutX() + getViewElementPadding();
	}

	/**
	 * ViewElementのレイアウトXを設定する。
	 *
	 * @param layoutX レイアウトX。
	 */
	default void setViewElementLayoutX(double layoutX) {
		this.setLayoutX(layoutX - getViewElementPadding());
	}

	/**
	 * ViewElementのレイアウトYを取得する。
	 *
	 * @return レイアウトX。
	 */
	default double getViewElementLayoutY() {
		return this.getLayoutY() + getViewElementPadding();
	}

	/**
	 * ViewElementのレイアウトYを設定する。
	 *
	 * @param layoutY レイアウトY。
	 */
	default void setViewElementLayoutY(double layoutY) {
		this.setLayoutY(layoutY - getViewElementPadding());
	}

	/**
	 * ViewElementの推奨幅を取得する。
	 *
	 * @return 推奨幅。
	 */
	default double getViewElementPrefWidth() {
		return this.getPrefWidth() - getViewElementPadding() * 2;
	}

	/**
	 * ViewElementの推奨高さを取得する。
	 *
	 * @return 推奨高さ。
	 */
	default double getViewElementPrefHeight() {
		return this.getPrefHeight() - getViewElementPadding() * 2;
	}

	// --------
	// implements javafx
	// --------
	/**
	 * 指定したイベントを起動します。デフォルトでは、イベントはステージからこのノードまで階層を通って移動します。<br>
	 * 検出されたイベント・フィルタに通知され、イベント・フィルタはイベントを使用できます。フィルタが使用しない場合は、このノードのイベント・ハンドラに通知されます。<br>
	 * これらもイベントを使用しない場合は、このノードに到達するために使用された経路を戻ることになります。検出されたすべてのイベント・ハンドラが呼び出され、これらがイベントを使用できます。<br>
	 * <br>
	 * このメソッドは、FXユーザー・スレッドで呼び出される必要があります。<br>
	 *
	 * @param e 起動するイベント。
	 * @see javafx.scene.Node#fireEvent
	 */
	void fireEvent(Event e);

	/**
	 * プロパティparentの値を取得します。
	 *
	 * @return このNodeの親ノードです。この{@code Node}がシーングラフに追加されていない場合、{@code Parent}はnullになります。
	 * @defaultValue null
	 * @see javafx.scene.Node#getParent
	 */
	Parent getParent();

	/**
	 * プロパティlayoutXの値を取得します。
	 *
	 * @return layoutX
	 * @see javafx.scene.Node#getLayoutX
	 */
	double getLayoutX();

	/**
	 * プロパティlayoutYの値を取得します。
	 *
	 * @return layoutY
	 * @see javafx.scene.Node#getLayoutY
	 */
	double getLayoutY();

	/**
	 * プロパティlayoutXの値を設定します。
	 *
	 * @param layoutX
	 * @see javafx.scene.Node#setLayoutX
	 */
	void setLayoutX(double layoutX);

	/**
	 * プロパティlayoutYの値を設定します。
	 *
	 * @param layoutY
	 * @see javafx.scene.Node#setLayoutY
	 */
	void setLayoutY(double layoutY);

	/**
	 * この{@code Parent}の子のリストを読取り専用リストとして取得します。<br>
	 * <br>
	 *
	 * @return この親の子ObservableListへの読取り専用アクセス。
	 * @see javafx.scene.Parent#getChildrenUnmodifiable
	 */
	List<Node> getChildrenUnmodifiable();

	/**
	 * プロパティprefWidthの値を取得します。
	 *
	 * @return prefWidth
	 * @see javafx.scene.layout.Region#getPrefWidth
	 */
	double getPrefWidth();

	/**
	 * プロパティprefHeightの値を取得します。
	 *
	 * @return prefHeight
	 * @see javafx.scene.layout.Region#getPrefWidth
	 */
	double getPrefHeight();

	/**
	 * プロパティprefWidthの値を取得します。
	 *
	 * @param prefWidth
	 * @see javafx.scene.layout.Region#setPrefWidth
	 */
	void setPrefWidth(double prefWidth);

	/**
	 * プロパティprefHeightの値を取得します。
	 *
	 * @param prefHeight
	 * @see javafx.scene.layout.Region#setPrefHeight
	 */
	void setPrefHeight(double prefHeight);
}
