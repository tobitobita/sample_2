using System.Runtime.Serialization;
namespace sample_xml_serialize
{
    [DataContract]
    public abstract class ネコ科 : 動物
    {
        [DataMember]
        private int id;

        [DataMember]
        private string 名前;

        public ネコ科(int id, string 名前)
        {
            this.id = id;
            this.名前 = 名前;
        }
    }
}