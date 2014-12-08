using System.Runtime.Serialization;
namespace sample_xml_serialize
{
    [DataContract]
    public class ネコ : ネコ科
    {
        [DataMember]
        private string 鳴き声;

        public ネコ(int id, string 名前, string 鳴き声)
            : base(id, 名前)
        {
            this.鳴き声 = 鳴き声;
        }
    }
}
