package com.piquet.service;

import com.piquet.pojo.InboundDetail;

public interface InboundDetailService {
    void update(InboundDetail inboundDetail);

    void delete(Integer id);
}
