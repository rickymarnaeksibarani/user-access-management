package com.gmf.user_management.config.MultipleDataSourceConfiguration.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ExternalRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExternalRepository(@Qualifier("mysqlDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Map<String, Object>> findContractsWithPartners(int limit, int offset, String filterByStatus, LocalDate filterByStart, LocalDate filterByEnd) {
        StringBuilder sql = new StringBuilder("""
           SELECT p.id, partner_id, contract_id, n.name, c.subject, c.number, c.start, c.end, c.status
           FROM partner_contracts p
           LEFT JOIN contracts c ON c.id = p.contract_id
           LEFT JOIN partners n ON n.id = p.partner_id
           WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (filterByStatus != null && !filterByStatus.isEmpty()) {
            sql.append(" AND c.status = ?");
            params.add(filterByStatus);
        }
        if (filterByStart != null) {
            sql.append(" AND c.start = ?");
            params.add(java.sql.Date.valueOf(filterByStart));
        }
        if (filterByEnd != null) {
            sql.append(" AND c.end = ?");
            params.add(java.sql.Date.valueOf(filterByEnd));
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    public Map<String, Object> findContractById(Long contractId) {
        String sql = """
       SELECT p.id, p.partner_id, p.contract_id, n.name, c.subject, c.number, c.start, c.end, c.status
       FROM partner_contracts p
       LEFT JOIN contracts c ON c.id = p.contract_id
       LEFT JOIN partners n ON n.id = p.partner_id
       WHERE p.id = ?
    """;
        try {
            return jdbcTemplate.queryForMap(sql, contractId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id Not found");
        }
    }

    public long countContracts(String filterByStatus, LocalDate filterByStart, LocalDate filterByEnd) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM partner_contracts p LEFT JOIN contracts c ON c.id = p.contract_id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (filterByStatus != null && !filterByStatus.isEmpty()) {
            sql.append(" AND c.status = ?");
            params.add(filterByStatus);
        }
        if (filterByStart != null) {
            sql.append(" AND c.start = ?");
            params.add(java.sql.Date.valueOf(filterByStart));
        }
        if (filterByEnd != null) {
            sql.append(" AND c.end = ?");
            params.add(java.sql.Date.valueOf(filterByEnd));
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Long.class);
    }
}
