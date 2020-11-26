package com.igkim.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Hdfs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;

    @JsonProperty(value = "LiveDataNodeCount")
    private int liveDatanodeCount;

    @JsonProperty(value = "DeadDataNodeCount")
    private int deadDatanodeCount;

    @JsonProperty(value = "NameNodeHeapTotalM")
    private double heapTotalMega;

    @JsonProperty(value = "NameNodeHeapUsedM")
    private double heapUsedMega;

    @JsonProperty(value = "TotalSize")
    private long capacityTotalByte;

    @JsonProperty(value = "UsedSize")
    private long capacityUsedByte;

    @JsonProperty(value = "FreeSize")
    private long capacityFreeByte;

    @JsonProperty(value = "ConvertedTotalSize")
    private String capacityTotalConvert;

    @JsonProperty(value = "ConvertedUsedSize")
    private String capacityUsedConvert;

    @JsonProperty(value = "ConvertedFreeSize")
    private String capacityFreeConvert;

    @JsonProperty(value = "PercentUsed")
    private double capacityUsedPercent;

    @JsonProperty(value = "FileCount")
    private long fileCount;

    @JsonProperty(value = "BlockCount")
    private long blockCount;

    @JsonProperty(value = "UnderReplicateBlockCount")
    private int underReplicateBlockCount;

    @JsonProperty(value = "MissingReplicateBlockCount")
    private int missReplicateBlockCount;

    @JsonProperty(value = "CurruptBlockCount")
    private int curruptBlockCount;

    @JsonProperty(value = "SafeMode")
    private boolean safemode;

    @JsonProperty(value = "StartedTime")
    private Timestamp startedTime;

    @JsonProperty(value = "CurrentTime")
    private Timestamp collectedTime;

}
