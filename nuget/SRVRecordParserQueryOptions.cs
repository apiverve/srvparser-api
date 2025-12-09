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
        /// Example: _http._tcp.example.com. 3600 IN SRV 10 60 80 server.example.com.
        /// </summary>
        [JsonProperty("record")]
        public string Record { get; set; }
    }
}
