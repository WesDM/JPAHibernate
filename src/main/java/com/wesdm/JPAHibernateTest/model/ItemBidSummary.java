package com.wesdm.JPAHibernateTest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ItemBidSummary implements Serializable {

    protected Long itemId;

    protected String name;

    protected LocalDateTime auctionEnd;

    protected BigDecimal highestBid;

    public ItemBidSummary(Long itemId, String name,
                          LocalDateTime auctionEnd, BigDecimal highestBid) {
        this.itemId = itemId;
        this.name = name;
        this.auctionEnd = auctionEnd;
        this.highestBid = highestBid;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getAuctionEnd() {
        return auctionEnd;
    }

    public BigDecimal getHighestBid() {
        return highestBid;
    }

    @Override
    public String toString() {
        return "ItemBidSummary{" +
            "itemId=" + itemId +
            ", name='" + name + '\'' +
            ", auctionEnd=" + auctionEnd +
            ", highestBid=" + highestBid +
            '}';
    }
}
