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
import java.util.*;

@Repository
public class PartnerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PartnerRepository(@Qualifier("mysqlDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Page<Map<String, Object>> findContractsWithPartners(
            PartnerDTO PartnerDTO,
            Pageable pageable)
    {
        StringBuilder sql = new StringBuilder(
       """
       SELECT p.id, p.partner_id, p.contract_id,
              COALESCE(n.name, 'Unknown') AS name,
              c.subject, c.number, c.start, c.end,
              COALESCE(
                  CASE WHEN c.status = 2 THEN 'Active'
                       WHEN c.status = 3 THEN 'Inactive'
                  END, 'Unknown'
              ) AS status
       FROM partner_contracts p
       INNER JOIN contracts c ON c.id = p.contract_id
       LEFT JOIN partners n ON n.id = p.partner_id
       WHERE c.status IS NOT NULL AND c.status <> 1
       """
        );
        //Final -> active
        //Finish -> inactive
        List<Object> params = new ArrayList<>();

        if (PartnerDTO.getFilterByStatus() != null && !PartnerDTO.getFilterByStatus().trim().isEmpty()) {
            sql.append(" AND c.status = ?");
            params.add(Integer.parseInt(PartnerDTO.getFilterByStatus()));
        } else {
            sql.append(" AND c.status IN (2,3)");
        }
        if (PartnerDTO.getFilterByStart() != null) {
            sql.append(" AND c.start >= ?");
            params.add(java.sql.Date.valueOf(PartnerDTO.getFilterByStart()));
        }
        if (PartnerDTO.getFilterByEnd() != null) {
            sql.append(" AND c.end <= ?");
            params.add(java.sql.Date.valueOf(PartnerDTO.getFilterByEnd()));
        }
        if (PartnerDTO.getSearchTerm() != null && !PartnerDTO.getSearchTerm().isEmpty()) {
            sql.append(" AND n.name LIKE ?");
            params.add("%" + PartnerDTO.getSearchTerm() + "%");
        }

        sql.append(" ORDER BY c.start DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        List<Map<String, Object>> content = jdbcTemplate.queryForList(sql.toString(), params.toArray());

        String countSql = """
                    SELECT COUNT(*) FROM partner_contracts p
                    INNER JOIN contracts c ON c.id = p.contract_id
                    LEFT JOIN partners n ON n.id = p.partner_id
                    WHERE c.status IS NOT NULL AND c.status <> 1
                    """;

        List<Object> countParams = new ArrayList<>();
        if (PartnerDTO.getFilterByStatus() != null && !PartnerDTO.getFilterByStatus().isEmpty()) {
            countSql += " AND c.status = ?";
            countParams.add(PartnerDTO.getFilterByStatus());
        }
        if (PartnerDTO.getFilterByStart() != null) {
            countSql += " AND c.start >= ?";
            countParams.add(java.sql.Date.valueOf(PartnerDTO.getFilterByStart()));
        }
        if (PartnerDTO.getFilterByEnd() != null) {
            countSql += " AND c.end <= ?";
            countParams.add(java.sql.Date.valueOf(PartnerDTO.getFilterByEnd()));
        }
        if (PartnerDTO.getSearchTerm() != null && !PartnerDTO.getSearchTerm().isEmpty()) {
            countSql += " AND n.name LIKE ?";
            countParams.add("%" + PartnerDTO.getSearchTerm() + "%");
        }

        long totalElements = Objects.requireNonNullElse(
                jdbcTemplate.queryForObject(countSql, Long.class, countParams.toArray()),
                0L
        );

        return new PageImpl<>(content, pageable, totalElements);
    }

    public Map<String, Object> findContractById(Long contractId) {
        String sql = """
       SELECT p.id, p.partner_id, p.contract_id, n.name, c.subject, c.number, c.start, c.end, CASE
           WHEN c.status = 2 THEN 'Active'
           WHEN c.status = 3 THEN 'Inactive'
       END AS status
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
       SELECT p.id, partner_id, contract_id, n.name, c.subject, c.number, c.start, c.end, CASE
           WHEN c.status = 2 THEN 'Active'
           WHEN c.status = 3 THEN 'Inactive'
       END AS status
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
     SELECT p.id, p.partner_id, p.contract_id,
     COALESCE(n.name, 'Unknown') AS name,
     c.subject, c.number, c.start, c.end,
        COALESCE(
            CASE WHEN c.status = 2 THEN 'Active'
                 WHEN c.status = 3 THEN 'Inactive'
            END, 'Unknown'
        ) AS status
     FROM partner_contracts p
     INNER JOIN contracts c ON c.id = p.contract_id
     LEFT JOIN partners n ON n.id = p.partner_id
     WHERE p.id = ? AND c.status IS NOT NULL AND c.status <> 1
     """;
        try {
            return jdbcTemplate.queryForList(sql, partnerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contracts found for the provided partner_id");
        }
    }
}
