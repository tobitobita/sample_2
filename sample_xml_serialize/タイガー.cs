using System.Runtime.Serialization;
namespace sample_xml_serialize
{
    [DataContract]
    public class タイガー : ネコ科
    {
        [DataMember]
        private long 牙の数;

        public タイガー(int id, string 名前)
            : base(id, 名前)
        {
            this.牙の数 = 4;
        }
    }
}
