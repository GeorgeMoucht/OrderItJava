package com.universtity.orderit.waiter.data.mapper;

import com.universtity.orderit.waiter.data.model.TableDto;
import com.universtity.orderit.waiter.domain.model.Table;

public class TableMapper {

    public static Table toDomain(TableDto dto) {
        Table.Reserved reserved = dto.reserved != null
                ? new Table.Reserved(dto.reserved.id, dto.reserved.name, dto.reserved.time)
                : null;

        return new Table(dto.id, dto.name, dto.state, dto.status, reserved);
    }
}
