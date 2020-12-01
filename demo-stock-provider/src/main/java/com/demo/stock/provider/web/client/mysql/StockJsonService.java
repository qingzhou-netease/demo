package com.demo.stock.provider.web.client.mysql;

import com.demo.stock.provider.web.entity.StockJson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author chenjiahan | chenjiahan@corp.netease.com | 2019/3/25
 **/
public class StockJsonService {

    private JdbcTemplate jdbcTemplate;

    public StockJsonService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StockJson getStockJson(String id) {
        List<StockJson> stocks = jdbcTemplate.query(String.format("select * from stock where code = '%s'", id), new StockJsonRowMapper());
        if (stocks == null || stocks.size() == 0) return null;
        return stocks.get(0);
    }

    class StockJsonRowMapper implements RowMapper<StockJson> {

        @Override
        public StockJson mapRow(ResultSet resultSet, int i) throws SQLException {
            StockJson stockJson = new StockJson();
            stockJson.setId(resultSet.getLong("id"));
            stockJson.setValue(resultSet.getString("value"));
            return stockJson;
        }
    }
}
