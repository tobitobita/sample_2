using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace sample_xml_serialize
{
    class Program
    {
        static void Main(string[] args)
        {
            var 家族 = new 家族("ネコ大好き家族です。");
            家族.ネコを飼う(new ネコ(1, "コタロウ", "にゃーん"));
            家族.ネコを飼う(new ネコ(2, "オオタロウ", "にゃー"));
            家族.ネコを飼う(new タイガー(3, "虎夫"));

            DataContractSerializer serializer = new DataContractSerializer(typeof(家族));
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Encoding = new UTF8Encoding(false);
            using (XmlWriter xw = XmlWriter.Create(@"C:\Users\makoto\Desktop\/sample.xml", settings))
            {
                serializer.WriteObject(xw, 家族);
            }
        }
    }
}
