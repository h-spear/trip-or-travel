package com.pjt.triptravel.attraction.repository.custom;

import com.pjt.triptravel.attraction.dto.attraction.AttractionAroundResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class AttractionRepositoryCustomImpl implements AttractionRepositoryCustom {

    private final NamedParameterJdbcTemplate template;

    public AttractionRepositoryCustomImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<AttractionAroundResult> findWithinRoundRange(BigDecimal latitude, BigDecimal longitude, Double radiusKm) {
        String sql = "SELECT *, (6371 * " +
                    "                ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(latitude)) " +
                    "                   * COS(RADIANS(longitude) - RADIANS(:longitude))    " +
                    "                   + SIN(RADIANS(:latitude)) * SIN(RADIANS(latitude)))) AS distance" +
                    "      , content_type.name AS content_type_name " +
                    "   FROM attraction_info " +
                    "   JOIN content_type " +
                    "  USING (content_type_id)" +
                    "  WHERE (6371 * " +
                    "             ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(latitude)) " +
                    "                * COS(RADIANS(longitude) - RADIANS(:longitude))    " +
                    "                + SIN(RADIANS(:latitude)) * SIN(RADIANS(latitude)))) <= :radiusKm " +
                    "  ORDER BY distance, content_id ";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("latitude", latitude)
                .addValue("longitude", longitude)
                .addValue("radiusKm", radiusKm);

        log.info("sql={}", sql);
        return template.query(sql, param, attractionAroundResultRowMapper());
    }

    private RowMapper<AttractionAroundResult> attractionAroundResultRowMapper() {
        return (rs, rowNum) -> AttractionAroundResult.builder()
                .id(rs.getLong("content_id"))
                .title(rs.getString("title"))
                .addr1(rs.getString("addr1"))
                .addr2(rs.getString("addr2"))
                .zipcode(rs.getString("zipcode"))
                .tel(rs.getString("tel"))
                .imageUrl(rs.getString("first_image"))
                .imageUrl2(rs.getString("first_image2"))
                .latitude(rs.getBigDecimal("latitude"))
                .longitude(rs.getBigDecimal("longitude"))
                .sidoCode(rs.getLong("sido_code"))
                .gugunCode(rs.getLong("gugun_code"))
                .mlevel(rs.getString("mlevel"))
                .readCount(rs.getInt("readcount"))
                .likeCount(rs.getInt("like_count"))
                .distance(rs.getDouble("distance"))
                .contentTypeId(rs.getLong("content_type_id"))
                .contentTypeName(rs.getString("content_type_name"))
                .build();
    }
}
