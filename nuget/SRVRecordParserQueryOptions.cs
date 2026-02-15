using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.SRVRecordParser
{
    /// <summary>
    /// Query options for the SRV Record Parser API
    /// </summary>
    public class SRVRecordParserQueryOptions
    {
        /// <summary>
        /// The SRV record string to parse
        /// </summary>
        [JsonProperty("record")]
        public string Record { get; set; }
    }
}
