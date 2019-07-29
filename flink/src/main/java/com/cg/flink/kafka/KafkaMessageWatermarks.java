package com.cg.flink.kafka;

import javax.annotation.Nullable;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import com.cg.po.bigdata.Message;

public class KafkaMessageWatermarks implements AssignerWithPeriodicWatermarks<Message> {
	private static final long serialVersionUID = 1L;
	private long currentTimestamp = Long.MIN_VALUE;

    @Override
    public long extractTimestamp(Message event, long previousElementTimestamp) {
        this.currentTimestamp = event.getTimestamp();
        return event.getTimestamp();
    }

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        Watermark watermark = new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
        return watermark;
    }

}
