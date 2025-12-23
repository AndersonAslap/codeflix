package com.codeflix.admin.catalogo.domain.pagination;

import java.util.List;

public record Pagination<T>(
        int page,
        int perPage,
        long total,
        List<T> items
) {
}
