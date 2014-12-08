using System.Collections;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace sample_xml_serialize
{
    [DataContract]
    [KnownType(typeof(ネコ))]
    [KnownType(typeof(タイガー))]
    public class 家族 : 集まり
    {
        [DataMember]
        private IEnumerable<動物> ネコたち;

        public 家族(string 概要)
            : base(概要)
        {
            ネコたち = new List<ネコ科>();
        }

        public void ネコを飼う(ネコ科 ネコ)
        {
            (this.ネコたち as IList).Add(ネコ);
        }
    }
}
