create table stock
(
    id    int auto_increment
        primary key,
    value varchar(1024) null,
    code  varchar(255)  null
);

INSERT INTO nsf_demo.stock (id, value, code) VALUES (1, '{"id":"SZ000002","name":"万  科Ａ(mysql)","dailyKLineAddr":"http://img1.money.126.net/chart/hs/kline/day/30/sz000002.png","openingPrice":"28.84","closingPrice":"29.19","currentPrice":"28.16","inPrice":"28.16","outPrice":"28.17","topTodayPrice":"28.92","bottomTodayPrice":"28.11"}', 'sz000002');
INSERT INTO nsf_demo.stock (id, value, code) VALUES (2, '{"id":"SZ000001","name":"平安银行(mysql)","dailyKLineAddr":"/img/1000001.png","openingPrice":"9.8","closingPrice":"9.84","currentPrice":"9.69","inPrice":"9.69","outPrice":"9.7","topTodayPrice":"9.8","bottomTodayPrice":"9.68"}', 'sz000001');
INSERT INTO nsf_demo.stock (id, value, code) VALUES (3, '{"id":"SZ000018","name":"神州长城(mysql)","dailyKLineAddr":"/img/1000018.png","openingPrice":"2.51","closingPrice":"2.62","currentPrice":"2.48","inPrice":"2.47","outPrice":"2.48","topTodayPrice":"2.53","bottomTodayPrice":"2.42"}', 'sz000018');
INSERT INTO nsf_demo.stock (id, value, code) VALUES (4, '{"id":"SH600425","name":"青松建化(mysql)","dailyKLineAddr":"/img/0600425.png","openingPrice":"3.0","closingPrice":"3.0","currentPrice":"2.93","inPrice":"2.92","outPrice":"2.93","topTodayPrice":"3.0","bottomTodayPrice":"2.92"}', 'sh600425');