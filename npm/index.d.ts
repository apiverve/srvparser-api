declare module '@apiverve/srvparser' {
  export interface srvparserOptions {
    api_key: string;
    secure?: boolean;
  }

  /**
   * Describes fields the current plan does not unlock. Locked fields arrive as null
   * in `data`; `locked_fields` names them, using dot paths for nested fields.
   * Absent when the plan unlocks everything.
   */
  export interface PremiumInfo {
    message: string;
    upgrade_url: string;
    locked_fields: string[];
  }

  export interface srvparserResponse {
    status: string;
    error: string | null;
    data: SRVRecordParserData;
    code?: number;
    premium?: PremiumInfo;
  }


  interface SRVRecordParserData {
      rawRecord:      null | string;
      parsed:         Parsed;
      serviceInfo:    ServiceInfo;
      interpretation: Interpretation;
      isValid:        boolean | null;
  }
  
  interface Interpretation {
      priorityExplanation: null | string;
      weightExplanation:   null | string;
      targetExplanation:   null | string;
  }
  
  interface Parsed {
      name:     null | string;
      service:  null | string;
      protocol: null | string;
      domain:   null | string;
      ttl:      number | null;
      class:    null | string;
      priority: number | null;
      weight:   number | null;
      port:     number | null;
      target:   null | string;
  }
  
  interface ServiceInfo {
      name:        null | string;
      description: null | string;
      defaultPort: number | null;
  }

  export default class srvparserWrapper {
    constructor(options: srvparserOptions);

    execute(callback: (error: any, data: srvparserResponse | null) => void): Promise<srvparserResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: srvparserResponse | null) => void): Promise<srvparserResponse>;
    execute(query?: Record<string, any>): Promise<srvparserResponse>;
  }
}
