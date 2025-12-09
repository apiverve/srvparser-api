// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     SRVRecordParserData data = Converter.fromJsonString(jsonString);

package com.apiverve.srvparser.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static SRVRecordParserData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(SRVRecordParserData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(SRVRecordParserData.class);
        writer = mapper.writerFor(SRVRecordParserData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// SRVRecordParserData.java

package com.apiverve.srvparser.data;

import com.fasterxml.jackson.annotation.*;

public class SRVRecordParserData {
    private String rawRecord;
    private Parsed parsed;
    private ServiceInfo serviceInfo;
    private Interpretation interpretation;
    private boolean isValid;

    @JsonProperty("raw_record")
    public String getRawRecord() { return rawRecord; }
    @JsonProperty("raw_record")
    public void setRawRecord(String value) { this.rawRecord = value; }

    @JsonProperty("parsed")
    public Parsed getParsed() { return parsed; }
    @JsonProperty("parsed")
    public void setParsed(Parsed value) { this.parsed = value; }

    @JsonProperty("service_info")
    public ServiceInfo getServiceInfo() { return serviceInfo; }
    @JsonProperty("service_info")
    public void setServiceInfo(ServiceInfo value) { this.serviceInfo = value; }

    @JsonProperty("interpretation")
    public Interpretation getInterpretation() { return interpretation; }
    @JsonProperty("interpretation")
    public void setInterpretation(Interpretation value) { this.interpretation = value; }

    @JsonProperty("is_valid")
    public boolean getIsValid() { return isValid; }
    @JsonProperty("is_valid")
    public void setIsValid(boolean value) { this.isValid = value; }
}

// Interpretation.java

package com.apiverve.srvparser.data;

import com.fasterxml.jackson.annotation.*;

public class Interpretation {
    private String priorityExplanation;
    private String weightExplanation;
    private String targetExplanation;

    @JsonProperty("priority_explanation")
    public String getPriorityExplanation() { return priorityExplanation; }
    @JsonProperty("priority_explanation")
    public void setPriorityExplanation(String value) { this.priorityExplanation = value; }

    @JsonProperty("weight_explanation")
    public String getWeightExplanation() { return weightExplanation; }
    @JsonProperty("weight_explanation")
    public void setWeightExplanation(String value) { this.weightExplanation = value; }

    @JsonProperty("target_explanation")
    public String getTargetExplanation() { return targetExplanation; }
    @JsonProperty("target_explanation")
    public void setTargetExplanation(String value) { this.targetExplanation = value; }
}

// Parsed.java

package com.apiverve.srvparser.data;

import com.fasterxml.jackson.annotation.*;

public class Parsed {
    private String name;
    private String service;
    private String protocol;
    private String domain;
    private long ttl;
    private String parsedClass;
    private long priority;
    private long weight;
    private long port;
    private String target;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("service")
    public String getService() { return service; }
    @JsonProperty("service")
    public void setService(String value) { this.service = value; }

    @JsonProperty("protocol")
    public String getProtocol() { return protocol; }
    @JsonProperty("protocol")
    public void setProtocol(String value) { this.protocol = value; }

    @JsonProperty("domain")
    public String getDomain() { return domain; }
    @JsonProperty("domain")
    public void setDomain(String value) { this.domain = value; }

    @JsonProperty("ttl")
    public long getTTL() { return ttl; }
    @JsonProperty("ttl")
    public void setTTL(long value) { this.ttl = value; }

    @JsonProperty("class")
    public String getParsedClass() { return parsedClass; }
    @JsonProperty("class")
    public void setParsedClass(String value) { this.parsedClass = value; }

    @JsonProperty("priority")
    public long getPriority() { return priority; }
    @JsonProperty("priority")
    public void setPriority(long value) { this.priority = value; }

    @JsonProperty("weight")
    public long getWeight() { return weight; }
    @JsonProperty("weight")
    public void setWeight(long value) { this.weight = value; }

    @JsonProperty("port")
    public long getPort() { return port; }
    @JsonProperty("port")
    public void setPort(long value) { this.port = value; }

    @JsonProperty("target")
    public String getTarget() { return target; }
    @JsonProperty("target")
    public void setTarget(String value) { this.target = value; }
}

// ServiceInfo.java

package com.apiverve.srvparser.data;

import com.fasterxml.jackson.annotation.*;

public class ServiceInfo {
    private String name;
    private String description;
    private long defaultPort;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("description")
    public String getDescription() { return description; }
    @JsonProperty("description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("default_port")
    public long getDefaultPort() { return defaultPort; }
    @JsonProperty("default_port")
    public void setDefaultPort(long value) { this.defaultPort = value; }
}