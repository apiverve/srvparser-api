declare module '@apiverve/srvparser' {
  export interface srvparserOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface srvparserResponse {
    status: string;
    error: string | null;
    data: SRVRecordParserData;
    code?: number;
  }


  interface SRVRecordParserData {
      rawRecord:      string;
      parsed:         Parsed;
      serviceInfo:    ServiceInfo;
      interpretation: Interpretation;
      isValid:        boolean;
  }
  
  interface Interpretation {
      priorityExplanation: string;
      weightExplanation:   string;
      targetExplanation:   string;
  }
  
  interface Parsed {
      name:     string;
      service:  string;
      protocol: string;
      domain:   string;
      ttl:      number;
      class:    string;
      priority: number;
      weight:   number;
      port:     number;
      target:   string;
  }
  
  interface ServiceInfo {
      name:        string;
      description: string;
      defaultPort: number;
  }

  export default class srvparserWrapper {
    constructor(options: srvparserOptions);

    execute(callback: (error: any, data: srvparserResponse | null) => void): Promise<srvparserResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: srvparserResponse | null) => void): Promise<srvparserResponse>;
    execute(query?: Record<string, any>): Promise<srvparserResponse>;
  }
}
