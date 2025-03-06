package com.gmf.user_management.config.partner.repository;

import com.gmf.user_management.config.partner.dto.PartnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
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
    public Page<Map<String, Object>> findContractsWithPartners(
            PartnerDTO dataSourceDTO,
            Pageable pageable) {

        StringBuilder sql = new StringBuilder("""
       SELECT p.id, partner_id, contract_id, n.name, c.subject, c.number, c.start, c.end, c.status
       FROM partner_contracts p
       LEFT JOIN contracts c ON c.id = p.contract_id
       LEFT JOIN partners n ON n.id = p.partner_id
       WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (dataSourceDTO.getFilterByStatus() != null && !dataSourceDTO.getFilterByStatus().isEmpty()) {
            sql.append(" AND c.status = ?");
            params.add(dataSourceDTO.getFilterByStatus());
        }
        if (dataSourceDTO.getFilterByStart() != null) {
            sql.append(" AND c.start >= ?");
            params.add(java.sql.Date.valueOf(dataSourceDTO.getFilterByStart()));
        }
        if (dataSourceDTO.getFilterByEnd() != null) {
            sql.append(" AND c.end <= ?");
            params.add(java.sql.Date.valueOf(dataSourceDTO.getFilterByEnd()));
        }
        if (dataSourceDTO.getSearchTerm() != null && !dataSourceDTO.getSearchTerm().isEmpty()) {
            sql.append(" AND n.name LIKE ?");
            params.add("%" + dataSourceDTO.getSearchTerm() + "%");

        }

//        sql.append(" LIMIT ? OFFSET ?");
        sql.append(" ORDER BY c.start DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        List<Map<String, Object>> content = jdbcTemplate.queryForList(sql.toString(), params.toArray());

        String countSql = "SELECT COUNT(*) FROM partner_contracts p " +
                "LEFT JOIN contracts c ON c.id = p.contract_id " +
                "LEFT JOIN partners n ON n.id = p.partner_id " +
                "WHERE 1=1";

        List<Object> countParams = new ArrayList<>();
        if (dataSourceDTO.getFilterByStatus() != null && !dataSourceDTO.getFilterByStatus().isEmpty()) {
            countSql += " AND c.status = ?";
            countParams.add(dataSourceDTO.getFilterByStatus());
        }
        if (dataSourceDTO.getFilterByStart() != null) {
            countSql += " AND c.start >= ?";
            countParams.add(java.sql.Date.valueOf(dataSourceDTO.getFilterByStart()));
        }
        if (dataSourceDTO.getFilterByEnd() != null) {
            countSql += " AND c.end <= ?";
            countParams.add(java.sql.Date.valueOf(dataSourceDTO.getFilterByEnd()));
        }
        if (dataSourceDTO.getSearchTerm() != null && !dataSourceDTO.getSearchTerm().isEmpty()) {
            countSql += " AND n.name LIKE ?";
            countParams.add("%" + dataSourceDTO.getSearchTerm() + "%");
        }

        long totalElements = jdbcTemplate.queryForObject(countSql, Long.class, countParams.toArray());

        return new PageImpl<>(content, pageable, totalElements);
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

    public Map<String, Object> findPartnerByContractId(Long contractId) {
        String sql = """
       SELECT p.id, partner_id, contract_id, n.name, c.subject, c.number, c.start, c.end, c.status
       FROM partner_contracts p
       LEFT JOIN contracts c ON c.id = p.contract_id
       LEFT JOIN partners n ON n.id = p.partner_id
       WHERE contract_id = ?
    """;
        try {
            return jdbcTemplate.queryForMap(sql, contractId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id Not found");
        }
    }

    public List<Map<String, Object>> findByPartnerId(Long partnerId) {
        String sql = """
           SELECT p.id, partner_id, contract_id, n.name, c.subject, c.number, c.start, c.end, c.status
           FROM partner_contracts p
           LEFT JOIN contracts c ON c.id = p.contract_id
           LEFT JOIN partners n ON n.id = p.partner_id
           WHERE p.partner_id = ?
        """;
        try {
            return jdbcTemplate.queryForList(sql, partnerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contracts found for the provided partner_id");
        }
    }
}
