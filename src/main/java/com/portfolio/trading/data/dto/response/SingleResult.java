package com.portfolio.trading.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> {

    private T data;
}
