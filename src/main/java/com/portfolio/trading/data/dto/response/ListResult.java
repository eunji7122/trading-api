package com.portfolio.trading.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> {

    private List<T> list;
}
