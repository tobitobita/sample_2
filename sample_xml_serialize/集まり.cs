using System.Runtime.Serialization;
namespace sample_xml_serialize
{
    [DataContract]
    public class 集まり
    {
        [DataMember]
        protected string 概要;

        public 集まり(string 概要)
        {
            this.概要 = 概要;
        }
 
    }
}
