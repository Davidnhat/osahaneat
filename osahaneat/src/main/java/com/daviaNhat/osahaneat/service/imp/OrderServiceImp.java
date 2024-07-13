package com.daviaNhat.osahaneat.service.imp;

import com.daviaNhat.osahaneat.payload.request.OrderRequest;

public interface OrderServiceImp {
    boolean insertOrder(OrderRequest orderRequest);
}
