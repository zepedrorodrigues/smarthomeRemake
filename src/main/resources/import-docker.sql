CREATE TABLE IF NOT EXISTS actuator_type
(
    actuator_type_name VARCHAR(255) NOT NULL PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS actuator_model
(
    actuator_model_name VARCHAR(255) NOT NULL PRIMARY KEY,
    actuator_type_name  VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS sensor_type
(
    sensor_type_id   VARCHAR(255) NOT NULL PRIMARY KEY,
    sensor_type_name VARCHAR(255),
    sensor_type_unit VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS sensor_model
(
    sensor_model_name VARCHAR(255) NOT NULL PRIMARY KEY,
    sensor_type_id    VARCHAR(255),
    FOREIGN KEY(sensor_type_id) REFERENCES sensor_type(sensor_type_id)
    );

CREATE TABLE IF NOT EXISTS device_type
(
    device_type_name VARCHAR(255) NOT NULL PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS house
(
    house_name    VARCHAR(255) NOT NULL PRIMARY KEY,
    city         VARCHAR(255),
    country      VARCHAR(255),
    latitude     DOUBLE,
    longitude    DOUBLE,
    street_name   VARCHAR(255),
    street_number VARCHAR(255),
    zip_code      VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS room
(
    room_id    VARCHAR(255) NOT NULL PRIMARY KEY,
    floor     INT,
    height    DOUBLE,
    house_name VARCHAR(255),
    length    DOUBLE PRECISION,
    room_name  VARCHAR(255),
    width     DOUBLE PRECISION,
    FOREIGN KEY(house_name) REFERENCES house(house_name)
    );

CREATE TABLE IF NOT EXISTS device
(
    device_id       VARCHAR(255) NOT NULL PRIMARY KEY,
    device_name     VARCHAR(255),
    device_status   BOOLEAN                NOT NULL,
    device_type_name VARCHAR(255),
    room_identity   VARCHAR(255),
    FOREIGN KEY(device_type_name) REFERENCES device_type(device_type_name),
    FOREIGN KEY(room_identity) REFERENCES room(room_id)
    );

CREATE TABLE IF NOT EXISTS sensor
(
    sensor_id        VARCHAR(255) NOT NULL PRIMARY KEY,
    device_id        VARCHAR(255),
    sensor_model_name VARCHAR(255),
    FOREIGN KEY(device_id) REFERENCES device(device_id),
    FOREIGN KEY(sensor_model_name) REFERENCES sensor_model(sensor_model_name)

    );

CREATE TABLE IF NOT EXISTS actuator
(
    actuator_id        VARCHAR(255) NOT NULL PRIMARY KEY,
    actuator_model_name VARCHAR(255),
    decimal_lower_limit DOUBLE,
    decimal_upper_limit DOUBLE,
    deviceid          VARCHAR(255),
    integer_lower_limit INTEGER,
    integer_upper_limit INTEGER,
    precision_value         INTEGER,
    FOREIGN KEY (deviceid) REFERENCES device(device_id)
    );

CREATE TABLE IF NOT EXISTS reading
(
    reading_id    VARCHAR(255) NOT NULL PRIMARY KEY,
    reading_value VARCHAR(255),
    sensor_id     VARCHAR(255),
    time_stamp    TIMESTAMP,
    FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id)
    );


-- Inserting data into the tables as previously provided
-- This data should now conform to the revised table definitions and constraints

-- table actuator_type
INSERT INTO actuator_type (actuator_type_name) VALUES
                                                   ('BlindsRoller'),
                                                   ('DecimalLimiter'),
                                                   ('Limiter'),
                                                   ('OnOffSwitch');

-- table actuator_model
INSERT INTO actuator_model (actuator_model_name, actuator_type_name) VALUES
                                                                         ('ActuatorOfBlindRoller', 'BlindsRoller'),
                                                                         ('ActuatorOfDecimalLimiter', 'DecimalLimiter'),
                                                                         ('ActuatorOfLimiter', 'Limiter'),
                                                                         ('ActuatorOfOnOffSwitch', 'OnOffSwitch');

-- table sensor_type
INSERT INTO sensor_type (sensor_type_id, sensor_type_name, sensor_type_unit) VALUES
                                                                                 ('TemperatureCelsius', 'Temperature', 'Celsius'),
                                                                                 ('ScalePercentagePercentage', 'ScalePercentage', 'Percentage'),
                                                                                 ('PowerConsumptionWatts', 'PowerConsumption', 'Watts'),
                                                                                 ('HumidityPercent', 'Humidity', 'Percent'),
                                                                                 ('OnOffNa', 'OnOff', 'Na'),
                                                                                 ('WindSpeedWindDirectionKmHrRadian', 'WindSpeedWindDirection', 'KmHrRadian'),
                                                                                 ('SunriseTime', 'Sunrise', 'Time'),
                                                                                 ('SunsetTime', 'Sunset', 'Time'),
                                                                                 ('DewPointCelsius', 'DewPoint', 'Celsius'),
                                                                                 ('SolarIrradianceWm2', 'SolarIrradiance', 'Wm2'),
                                                                                 ('AveragePowerConsumptionWatts', 'AveragePowerConsumption', 'Watts'),
                                                                                 ('ElectricEnergyConsumptionWh', 'ElectricEnergyConsumption', 'Wh');

-- table sensor_model
INSERT INTO sensor_model (sensor_model_name, sensor_type_id) VALUES
                                                                 ('SensorOfTemperature', 'TemperatureCelsius'),
                                                                 ('SensorOfScalePercentage', 'ScalePercentagePercentage'),
                                                                 ('SensorOfPowerConsumption', 'PowerConsumptionWatts'),
                                                                 ('SensorOfHumidity', 'HumidityPercent'),
                                                                 ('SensorOfOnOff', 'OnOffNa'),
                                                                 ('SensorOfWind', 'WindSpeedWindDirectionKmHrRadian'),
                                                                 ('SensorOfSunrise', 'SunriseTime'),
                                                                 ('SensorOfSunset', 'SunsetTime'),
                                                                 ('SensorOfDewPoint', 'DewPointCelsius'),
                                                                 ('SensorOfSolarIrradiance', 'SolarIrradianceWm2'),
                                                                 ('SensorOfAveragePowerConsumption', 'AveragePowerConsumptionWatts'),
                                                                 ('SensorOfElectricEnergyConsumption', 'ElectricEnergyConsumptionWh');

-- table device_type
INSERT INTO device_type (device_type_name) VALUES
                                               ('Default'),
                                               ('GridPowerMeter'),
                                               ('PowerSourcePowerMeter');

-- table house
INSERT INTO house (house_name, city, country, latitude, longitude, street_name, street_number, zip_code) VALUES
    ('TheHouse', 'Porto', 'Portugal', 44, 55, 'aquela rua', '22', '4200-500');

-- table room
INSERT INTO room (room_id, floor, height, house_name, length, room_name, width) VALUES
                                                                                    ('e21f52ec-7004-4c66-b011-248599d81e3a', 0, 3, 'TheHouse', 5, 'Living Room', 4),
                                                                                    ('9721346a-474b-4e41-8235-0af529fba7d5', 1, 3, 'TheHouse', 4, 'Master Bedroom', 4),
                                                                                    ('ad121731-b3cb-4125-a8fd-b691a01d4c77', 0, 3, 'TheHouse', 4, 'Kitchen', 3);
('b34d53a2-e7cd-451a-9e3d-c481b07e4f55', 0, 3, 'TheHouse', 122, 'Kitchen', 75);

-- table device
INSERT INTO device (device_id, device_name, device_status, device_type_name, room_identity) VALUES
                                                                                                ('ebb2d7c8-0952-47bf-b818-8e27088ca53a', 'Windmill Power Meter', true, 'PowerSourcePowerMeter', 'e21f52ec-7004-4c66-b011-248599d81e3a'),
                                                                                                ('6f78b49c-e55a-478b-a173-ee4548781cd4', 'EDP power meter', true, 'GridPowerMeter', 'ad121731-b3cb-4125-a8fd-b691a01d4c77'),
                                                                                                ('f01004db-5d37-4592-928a-6cfb1e6428cd', 'Thermostat', true, 'Default', 'ad121731-b3cb-4125-a8fd-b691a01d4c77'),
                                                                                                ('c45e7894-7427-4848-8c9d-5aed1c8d9ec5', 'Thermostat', true, 'Default', '9721346a-474b-4e41-8235-0af529fba7d5'),
                                                                                                ('44ad9efa-6b89-485d-96ee-255083bcbbff', 'Blind Roller', true, 'Default', '9721346a-474b-4e41-8235-0af529fba7d5'),
                                                                                                ('41b7b867-a99f-4171-9d12-5a5e25601aec', 'Blind Roller', true, 'Default', 'e21f52ec-7004-4c66-b011-248599d81e3a'),
                                                                                                ('08504a80-4818-43d0-91b9-1f2da26fa87d', 'Air Conditioner', true, 'Default', 'e21f52ec-7004-4c66-b011-248599d81e3a'),
                                                                                                ('28a3fddb-e558-41ea-9efb-0683357b9e67',
                                                                                                 'Central Heating',
                                                                                                 true, 'Default',
                                                                                                 '9721346a-474b-4e41-8235-0af529fba7d5'),
                                                                                                ('0167fb4a-d6b6-4d61-9fe9-7aec1a72f032', 'Alarm System', true, 'Default', 'e21f52ec-7004-4c66-b011-248599d81e3a'),
                                                                                                ('e4dc3aa4-25c3-4d9f-9b8c-6e4ef243a8f1', 'Solar Panel Power Meter', true, 'PowerSourcePowerMeter', 'e21f52ec-7004-4c66-b011-248599d81e3a');

-- table sensor
INSERT INTO sensor (sensor_id, device_id, sensor_model_name) VALUES
                                                                 ('2ca95c92-5a2e-4322-9e4d-6f6a954936a4', 'ebb2d7c8-0952-47bf-b818-8e27088ca53a', 'SensorOfPowerConsumption'),
                                                                 ('cbb76d06-4175-4c89-a5df-df662c5e0ad5', '41b7b867-a99f-4171-9d12-5a5e25601aec', 'SensorOfScalePercentage'),
                                                                 ('2dc8bab9-7268-40ff-a980-2ce175587fd9', '44ad9efa-6b89-485d-96ee-255083bcbbff', 'SensorOfScalePercentage'),
                                                                 ('17080dc0-9213-4de8-9ba3-1917b3b16085', 'f01004db-5d37-4592-928a-6cfb1e6428cd', 'SensorOfTemperature'),
                                                                 ('4187cfe0-34b6-4719-9811-847046b871cf', 'c45e7894-7427-4848-8c9d-5aed1c8d9ec5', 'SensorOfTemperature'),
                                                                 ('37953320-0cbb-44fe-9d9a-534c5212dc7f', '6f78b49c-e55a-478b-a173-ee4548781cd4', 'SensorOfPowerConsumption');

-- table actuator
INSERT INTO actuator (actuator_id, actuator_model_name, decimal_lower_limit, decimal_upper_limit, device_id, integer_lower_limit, integer_upper_limit, precision_value) VALUES
                                                                                                                                                                            ('46520740-6f3b-43e3-b407-512004ca0d17', 'ActuatorOfBlindRoller', null, null, '41b7b867-a99f-4171-9d12-5a5e25601aec', null, null, null),
                                                                                                                                                                            ('1788f71f-f3ef-42d8-bf34-fd93c5eb1544', 'ActuatorOfBlindRoller', null, null, '44ad9efa-6b89-485d-96ee-255083bcbbff', null, null, null),
                                                                                                                                                                            ('a109d123-040d-4e08-9dcc-c17472b56f87', 'ActuatorOfOnOffSwitch', null, null, '0167fb4a-d6b6-4d61-9fe9-7aec1a72f032', null, null, null),
                                                                                                                                                                            ('271cb3ee-7c6e-46c7-83cb-1e4e3e708e3d', 'ActuatorOfOnOffSwitch', null, null, '08504a80-4818-43d0-91b9-1f2da26fa87d', null, null, null),
                                                                                                                                                                            ('7d3991c7-dba5-4875-9234-bd60165c18fa', 'ActuatorOfOnOffSwitch', null, null, '28a3fddb-e558-41ea-9efb-0683357b9e67', null, null, null);


-- table reading

INSERT INTO reading (reading_id, reading_value, sensor_id, time_stamp) VALUES ('7a85932f-39ff-4fea-8b12-0c87821353ae', '25', '1', '2024-04-25 12:00:00.000000'),
                                                                              ('00d6dece-42e0-4d04-ae5c-ca6fe09c5d7c', '15', '2', '2024-04-25 12:00:10.000000'),
                                                                              ('e42e84d5-02fb-4a00-827d-36b840fc3197', '12', '3', '2024-04-25 12:00:00.000000'),
                                                                              ('5ec07ab6-fcad-4526-8532-83d5540fc17e', '5', '4', '2024-04-25 12:00:10.000000'),
                                                                              ('410febac-68a3-4ab9-8b5a-3e3c6d060e17', '100', '5', '2024-04-25 12:00:00.000000'),
                                                                              ('34e4c998-42d1-4790-8665-89176112f3cd', '25', '6', '2024-04-25 12:00:00.000000'),


-- sensor: 2ca95c92-5a2e-4322-9e4d-6f6a954936a4 (SensorOfPowerConsumption)

                                                                              ('7fcd8270-f317-45ff-90f5-dbb4a2f06b3e', '3562.25', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:00.000000'),
                                                                              ('a1a96f93-4aef-487d-83a7-2f768b3fef71', '4826.71', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:06.000000'),
                                                                              ('cdfb0e90-0c0d-41f7-9c7e-3651e5f95dc7', '2763.50', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:12.000000'),
                                                                              ('4ebace20-abe4-482e-84f2-2efcb1f63dc7', '4231.48', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:18.000000'),
                                                                              ('8d2b2f43-92e8-49c6-8f7f-108a0df18fd4', '1324.15', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:24.000000'),
                                                                              ('5d4ab67b-8e6d-4ab8-8e6d-7f87b3d7c4f1', '3175.39', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:30.000000'),
                                                                              ('b6e3b5e1-f7ae-4ff6-a5e1-bd3a1a36e7bc', '2987.26', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:36.000000'),
                                                                              ('25f7db3b-8f3c-4b8a-95e1-7428d3f8e2a9', '1926.85', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:42.000000'),
                                                                              ('ac9d33e4-4f0f-4ef9-86a7-ea7c1a7c8d1b', '2574.92', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:48.000000'),
                                                                              ('7d5f85f0-9f4c-4f1f-8c1f-5d5f85e1c4f2', '3875.49', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:00:54.000000'),

                                                                              ('1b9c7f4a-6a21-4516-9f30-4e4c0e9348e9', '275.52', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:00.000000'),
                                                                              ('3b1d69f2-7489-4cf7-a252-c3a7b0dd9c38', '295.16', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:06.000000'),
                                                                              ('5c3e27d4-8b48-47da-b540-4b2331bfa527', '314.76', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:12.000000'),
                                                                              ('7d4f18b5-9c76-4921-927b-4b0e1b6fb5f0', '285.34', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:18.000000'),
                                                                              ('9e5d07c6-a381-4d68-9c0b-1f0d58b2d759', '305.41', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:24.000000'),
                                                                              ('bf6c98d7-b542-417d-9d2a-3b5e7b1c0d84', '321.18', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:30.000000'),
                                                                              ('df7db8e8-d653-47e8-8e4b-3f0e7b2d1e95', '297.62', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:36.000000'),
                                                                              ('efa8e9f9-e764-48f9-9f3b-4f1e9b3c1fa6', '310.25', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:42.000000'),
                                                                              ('0a19b0fa-f875-4a0b-9f3c-5f2e9c3d2fb7', '299.48', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:48.000000'),
                                                                              ('1b2a1c1b-1097-4b0b-9f3d-6f3e9d4e3fc8', '289.17', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 12:01:54.000000'),

                                                                              ('2za96g92-5a2f-4322-9g5d-6f6g954936g4', '212.34', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:00.000000'),
                                                                              ('3zb07h03-6b3g-5433-0h6e-7g7h065047h5', '217.89', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:06.000000'),
                                                                              ('4zc18i14-7c4h-6544-1i7f-8h8i176158i6', '220.67', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:12.000000'),
                                                                              ('5zd29j25-8d5i-7655-2j8g-9i9j287269j7', '224.23', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:18.000000'),
                                                                              ('6ze30k36-9e6j-8766-3k9h-aj0k398370k8', '229.54', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:24.000000'),
                                                                              ('7zf41l47-af7k-9877-4la0-bk1l409481l9', '230.78', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:30.000000'),
                                                                              ('8zg52m58-bg8l-0b88-5mb1-cl2m510592m0', '235.65', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:36.000000'),
                                                                              ('9zh63n69-ch9m-1c99-6nc2-dm3n6216a3n1', '238.91', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:42.000000'),
                                                                              ('0zi74o70-di0n-2daa-7od3-em4o7327b4o2', '240.34', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:48.000000'),
                                                                              ('1zj85p81-ej1o-3ebb-8pe4-fm5p8438c5p3', '245.77', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-25 13:00:54.000000'),

                                                                              ('4po07a01-po0b-0po0-4oa1-pb1p482e1o0p', '225', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:00.000000'),
                                                                              ('5qp18b12-qp1c-1qp1-5pb2-qc2q593f1p1q', '230', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:06.000000'),
                                                                              ('6rq29c23-rq2d-2rq2-6qc3-rd3r604g2q2r', '235', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:12.000000'),
                                                                              ('7sr30d34-sr3e-3sr3-7rd4-se4s715h3r3s', '240', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:18.000000'),
                                                                              ('8ts41e45-ts4f-4ts4-8se5-tf5t826i4s4t', '245', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:24.000000'),
                                                                              ('9ut52f56-ut5g-5ut5-9tf6-ug6u937j5t5u', '250', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:30.000000'),
                                                                              ('0vu63g67-vu6h-6vu6-avf7-vh7v048k6u6v', '255', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:36.000000'),
                                                                              ('1wv74h78-wv7i-7wv7-bwg8-wi8w159l7v7w', '260', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:42.000000'),
                                                                              ('2xw85i89-xw8j-8xw8-cxh9-xj9x260m8w8x', '265', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:48.000000'),
                                                                              ('3xy96j90-xy9k-9xy9-dyi0-yk0y371n9x9y', '270', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 09:00:54.000000'),

                                                                              ('e2f3g4h5-5i6j-7k8l-9m0n-1o2p-3q4r-5s6t', '320', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:08:00.000000'),
                                                                              ('f3g4h5i6-6j7k-8l9m-0n1o-2p3q-4r5s-6t7u', '315', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:10:00.000000'),
                                                                              ('g4h5i6j7-7k8l-9m0n-1o2p-3q4r-5s6t-7u8v', '310', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:12:00.000000'),
                                                                              ('h5i6j7k8-8l9m-0n1o-2p3q-4r5s-6t7u-8v9w', '305', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:14:00.000000'),
                                                                              ('i6j7k8l9-9m0n-1o2p-3q4r-5s6t-7u8v-9w0x', '300', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:16:00.000000'),
                                                                              ('j7k8l9m0-0n1o-2p3q-4r5s-6t7u-8v9w-0x1y', '295', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:18:00.000000'),
                                                                              ('k8l9m0n1-1o2p-3q4r-5s6t-7u8v-9w0x-1y2z', '290', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:20:00.000000'),
                                                                              ('l9m0n1o2-2p3q-4r5s-6t7u-8v9w-0x1y-2z3a', '285', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:22:00.000000'),
                                                                              ('m0n1o2p3-3q4r-5s6t-7u8v-9w0x-1y2z-3a4b', '280', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:24:00.000000'),
                                                                              ('n1o2p3q4-4r5s-6t7u-8v9w-0x1y-2z3a-4b5c', '275', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 10:26:00.000000'),

                                                                              ('k0l1m2n3-4o5p-6q7r-8s9t-0u1v-2w3x', '200', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 23:59:40.000000'),
                                                                              ('l1m2n3o4-5p6q-7r8s-9t0u-1v2w-3x4y', '250', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 23:59:45.000000'),
                                                                              ('m2n3o4p5-6q7r-8s9t-0u1v-2w3x-4y5z', '300', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 23:59:50.000000'),
                                                                              ('n3o4p5q6-7r8s-9t0u-1v2w-3x4y-5z6a', '350', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 23:59:55.000000'),
                                                                              ('o4p5q6r7-8s9t-0u1v-2w3x-4y5z-6a7b', '400', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-26 23:59:57.000000'),
                                                                              ('p5q6r7s8-9t0u-1v2w-3x4y-5z6a-7b8c', '450', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-27 00:00:00.000000'),
                                                                              ('q6r7s8t9-0u1v-2w3x-4y5z-6a7b-8c9d', '500', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-27 00:00:02.000000'),
                                                                              ('r7s8t9u0-1v2w-3x4y-5z6a-7b8c-9d0e', '550', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-27 00:00:04.000000'),
                                                                              ('s8t9u0v1-2w3x-4y5z-6a7b-8c9d-0e1f', '600', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-27 00:00:06.000000'),
                                                                              ('t9u0v1w2-3x4y-5z6a-7b8c-9d0e-1f2g', '650', '2ca95c92-5a2e-4322-9e4d-6f6a954936a4', '2024-04-27 00:00:08.000000'),


-- sensor: cbb76d06-4175-4c89-a5df-df662c5e0ad5 (SensorOfScalePercentage)

                                                                              ('7e4fbe24-3c7f-47f7-84b2-9e7f0a6b7c91', '12.50', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:00.000000'),
                                                                              ('e7d2c7f4-4e87-4e1c-95f3-3c7f0c5e7b91', '74.62', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:06.000000'),
                                                                              ('b3d7c4f5-6e7c-4e7d-85f3-3e7f0a5e6c91', '35.40', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:12.000000'),
                                                                              ('d4f7c3e5-7e87-4f7d-85f4-3d7f0b5e7c91', '89.25', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:18.000000'),
                                                                              ('e4f7b3d5-8e87-4d7e-95f4-3e7f0b5d7c91', '46.50', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:24.000000'),
                                                                              ('f7c4d3b5-9e87-4d7f-85f5-3e7f0a5d6c91', '15.25', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:30.000000'),
                                                                              ('d7b4c5e6-ae87-4d7e-95f5-3f7f0b5d6c91', '82.35', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:36.000000'),
                                                                              ('f4d7b3c5-be87-4d7f-85f6-3f7f0a5d7c91', '65.45', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:42.000000'),
                                                                              ('e7f4b5d3-ce87-4d7e-95f6-3f7f0b5d6c91', '90.25', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:48.000000'),
                                                                              ('c7f4d5b6-de87-4d7f-85f7-3f7f0a5d7c91', '42.15', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:00:54.000000'),

                                                                              ('2c0d25f9-60bc-4700-9f60-7c4e2c5b5fa1', '85.21', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:00.000000'),
                                                                              ('3e1f37c0-71cd-4e01-9f70-8d5e3c6d6fb2', '73.56', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:06.000000'),
                                                                              ('4f2f48d1-82de-4f12-9f80-9e6f4d7e7fc3', '91.34', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:12.000000'),
                                                                              ('5g3g59e2-93ef-5013-9f90-af7f5e8f8fd4', '68.44', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:18.000000'),
                                                                              ('6h4h60f3-a4fg-5114-9fa0-bg8g6f9g9fe5', '80.12', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:24.000000'),
                                                                              ('7i5i71g4-b5gh-5215-9fb0-ch9h7g0h0gf6', '75.89', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:30.000000'),
                                                                              ('8j6j82h5-c6hi-5316-9fc0-dh0i8h1i1gh7', '88.45', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:36.000000'),
                                                                              ('9k7k93i6-d7ij-5417-9fd0-eh1j9i2j2gi8', '65.73', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:42.000000'),
                                                                              ('0l8l04j7-e8jk-5518-9fe0-fh2k0j3k3hj9', '70.52', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:48.000000'),
                                                                              ('1m9m15k8-f9kl-5619-9ff0-gh3l1k4l4ik0', '77.90', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 12:01:54.000000'),

                                                                              ('3yk07p82-fk2o-4fcc-9pf5-gm6q9539d6q4', '70.23', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:00.000000'),
                                                                              ('4zl18q93-gl3p-5gdd-0qf6-hn7r064a7r5', '72.87', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:06.000000'),
                                                                              ('5am29r04-hm4q-6hee-1qg7-io8s175b8s6', '68.34', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:12.000000'),
                                                                              ('6bn30s15-in5r-7iff-2rh8-jp9t286c9t7', '75.12', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:18.000000'),
                                                                              ('7co41t26-jo6s-8jgg-3si9-kq0u397d0u8', '76.45', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:24.000000'),
                                                                              ('8dp52u37-ko7t-9khh-4tj0-lr1v408e1v9', '73.78', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:30.000000'),
                                                                              ('9eq63v48-lo8u-0lii-5uk1-ms2w519f2w0', '71.56', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:36.000000'),
                                                                              ('0fr74w59-mo9v-1mjj-6vl2-nt3x62ag3x1', '78.92', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:42.000000'),
                                                                              ('1gs85x60-no0w-2nkk-7wm3-ou4y73bh4y2', '74.11', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:48.000000'),
                                                                              ('2ht96y71-op1x-3oll-8xn4-pv5z84ci5z3', '79.34', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-25 13:00:54.000000'),

                                                                              ('4vy07k01-vy0l-0vy0-4ak1-vl1v482e1a0v', '76', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:00.000000'),
                                                                              ('5wz18l12-wz1m-1wz1-5bl2-wm2w593f1b1w', '78', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:06.000000'),
                                                                              ('7yd229c34-yd2o-3yd2-7dn4-yo4y715h3d2y', '82', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:12.000000'),
                                                                              ('8ze331e45-ze3p-4ze3-8eo5-zp5z826i4e3z', '84', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:18.000000'),
                                                                              ('9af442f56-af4q-5af4-9fp6-ap6a937j5f4a', '86', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:24.000000'),
                                                                              ('0bg553g67-bg5r-6bg5-ago7-br7b048k6g5b', '88', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:30.000000'),
                                                                              ('1ch664h78-ch6s-7ch6-bhp8-cs8c159l7h6c', '90', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:36.000000'),
                                                                              ('2di775i89-di7t-8di7-ciq9-dt9d260m8i7d', '92', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:42.000000'),
                                                                              ('3ej886j90-ej8u-9ej8-djr0-eu0e371n9j8e', '94', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:48.000000'),
                                                                              ('4fk997k01-fk9v-0fk9-ekr1-fv1f482e1k9f', '96', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 09:00:54.000000'),

                                                                              ('r5s6t7u8-8v9w-0x1y-2z3a-4b5c-6d7e-8f9g', '80', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:06:00.000000'),
                                                                              ('s6t7u8v9-9w0x-1y2z-3a4b-5c6d-7e8f-9g0h', '85', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:08:00.000000'),
                                                                              ('t7u8v9w0-0x1y-2z3a-4b5c-6d7e-8f9g-0h1i', '90', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:10:00.000000'),
                                                                              ('u8v9w0x1-1y2z-3a4b-5c6d-7e8f-9g0h-1i2j', '95', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:12:00.000000'),
                                                                              ('v9w0x1y2-2z3a-4b5c-6d7e-8f9g-0h1i-2j3k', '100', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:14:00.000000'),
                                                                              ('w0x1y2z3-3a4b-5c6d-7e8f-9g0h-1i2j-3k4l', '55', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:16:00.000000'),
                                                                              ('x1y2z3a4-4b5c-6d7e-8f9g-0h1i-2j3k-4l5m', '60', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:18:00.000000'),
                                                                              ('y2z3a4b5-5c6d-7e8f-9g0h-1i2j-3k4l-5m6n', '65', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:20:00.000000'),
                                                                              ('z3a4b5c6-6d7e-8f9g-0h1i-2j3k-4l5m-6n7o', '70', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:22:00.000000'),
                                                                              ('a4b5c6d7-7e8f-9g0h-1i2j-3k4l-5m6n-7o8p', '75', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 10:24:00.000000'),

                                                                              ('u0v1w2x3-4y5z-6a7b-8c9d-0e1f-2g3h', '60', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:32.000000'),
                                                                              ('w2x3y4z5-6a7b-8c9d-0e1f-2g3h-4i5j', '70', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:36.000000'),
                                                                              ('y4z5a6b7-8c9d-0e1f-2g3h-4i5j-6k7l', '80', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:40.000000'),
                                                                              ('a6b7c8d9-0e1f-2g3h-4i5j-6k7l-8m9n', '90', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:44.000000'),
                                                                              ('c8d9e0f1-2g3h-4i5j-6k7l-8m9n-0o1p', '100', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:48.000000'),
                                                                              ('e0f1g2h3-4i5j-6k7l-8m9n-0o1p-2q3r', '110', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:52.000000'),
                                                                              ('g2h3i4j5-6k7l-8m9n-0o1p-2q3r-4s5t', '120', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-26 23:59:56.000000'),
                                                                              ('v1w2x3y4-5z6a-7b8c-9d0e-1f2g-3h4i', '65', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-27 00:00:04.000000'),
                                                                              ('x3y4z5a6-7b8c-9d0e-1f2g-3h4i-5j6k', '75', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-27 00:00:08.000000'),
                                                                              ('z5a6b7c8-9d0e-1f2g-3h4i-5j6k-7l8m', '85', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-27 00:00:12.000000'),
                                                                              ('b7c8d9e0-1f2g-3h4i-5j6k-7l8m-9n0o', '95', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-27 00:00:16.000000'),
                                                                              ('d9e0f1g2-3h4i-5j6k-7l8m-9n0o-1p2q', '105', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-27 00:00:20.000000'),
                                                                              ('f1g2h3i4-5j6k-7l8m-9n0o-1p2q-3r4s', '115', 'cbb76d06-4175-4c89-a5df-df662c5e0ad5', '2024-04-27 00:00:24.000000'),


-- sensor: 2dc8bab9-7268-40ff-a980-2ce175587fd9 (SensorOfScalePercentage)

                                                                              ('4a5f6b3e-3e8d-42a1-94b3-5a5f6e3b8d4c', '36.20', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:00.000000'),
                                                                              ('e6d5b4a7-4e9d-4b6f-94b3-6a5f6e3b9d4c', '55.70', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:06.000000'),
                                                                              ('b6a4c5d7-5e9d-4b7f-94b3-6b5f6e3b9d4c', '82.90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:12.000000'),
                                                                              ('c7b5a4d6-6e9d-4b8f-94b3-7b5f6e3b9d4c', '47.35', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:18.000000'),
                                                                              ('e6f5b7a4-7e9d-4b9f-94b3-7a5f6e3b9d4c', '23.40', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:24.000000'),
                                                                              ('b7c5d6a4-8e9d-4b0f-94b3-8b5f6e3b9d4c', '64.75', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:30.000000'),
                                                                              ('c6a5b7d5-9e9d-4b1f-94b3-8a5f6e3b9d4c', '38.90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:36.000000'),
                                                                              ('d7f5a4b6-0e9d-4b2f-94b3-9b5f6e3b9d4c', '50.25', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:42.000000'),
                                                                              ('e5b7d6a4-1e9d-4b3f-94b3-9a5f6e3b9d4c', '76.85', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:48.000000'),
                                                                              ('f6d5a7b4-2e9d-4b4f-94b3-0a5f6e3b9d4c', '49.50', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:00:54.000000'),

                                                                              ('2n0n26l9-g0lm-571a-9fg0-hg4m2l5m5jl1', '60.23', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:00.000000'),
                                                                              ('3o1o37m0-h1mn-581b-9fh0-ih5n3m6n6km2', '77.14', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:06.000000'),
                                                                              ('4p2p48n1-i2no-591c-9fi0-jh6o4n7o7ln3', '72.39', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:12.000000'),
                                                                              ('5q3q59o2-j3op-5a2d-9fj0-ki7p5o8p8mo4', '81.27', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:18.000000'),
                                                                              ('6r4r60p3-k4pq-5b3e-9fk0-li8q6p9q9np5', '90.36', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:24.000000'),
                                                                              ('7s5s71q4-l5qr-5c4f-9fl0-mj9r7q0r0oq6', '79.43', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:30.000000'),
                                                                              ('8t6t82r5-m6rs-5d5g-9fm0-nk0s8r1s1pr7', '65.78', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:36.000000'),
                                                                              ('9u7u93s6-n7st-5e6h-9fn0-ol1t9s2t2qs8', '74.56', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:42.000000'),
                                                                              ('0v8v04t7-o8tu-5f7i-9fo0-pm2u0t3u3rt9', '84.92', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:48.000000'),
                                                                              ('1w9w15u8-p9uv-5g8j-9fp0-qn3v1u4v4su0', '91.15', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 12:01:54.000000'),

                                                                              ('3wa94z94-oa2y-4xww-9ya5-zb6z9539d6z4', '50.45', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:00.000000'),
                                                                              ('4xb05a05-pb3z-5yxx-0zb6-ac7a064a7a5', '45.67', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:06.000000'),
                                                                              ('5yc16b16-qc4a-6zyy-1ac7-bd8b175b8b6', '48.90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:12.000000'),
                                                                              ('6zd27c27-rd5b-7azz-2bd8-ce9c286c9c7', '52.34', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:18.000000'),
                                                                              ('7ae38d38-se6c-8baa-3ce9-df9d397d0d8', '55.78', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:24.000000'),
                                                                              ('8bf49e49-tf7d-9cbb-4df0-eg0e408e1e9', '49.23', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:30.000000'),
                                                                              ('9cg50f50-ug8e-0dcc-5eg1-fh1f519f2f0', '47.45', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:36.000000'),
                                                                              ('0dh61g61-vh9f-1edd-6fh2-gi2g62ag3g1', '51.90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:42.000000'),
                                                                              ('1ei72h72-wi0g-2fee-7gi3-hj3h73bh4h2', '46.78', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:48.000000'),
                                                                              ('2fj83i83-xj1h-3gff-8hj4-ik4i84ci5i3', '53.12', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-25 13:00:54.000000'),

                                                                              ('f0g1a7d5-4fa5-4866-903e-9bf43aa27e0b', '65', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:00:00.000000'),
                                                                              ('g2h1a7d5-4fa5-4866-903e-9bf43aa27e0b', '70', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:01:00.000000'),
                                                                              ('i3j1a7d5-4fa5-4866-903e-9bf43aa27e0b', '75', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:02:00.000000'),
                                                                              ('k4l1a7d5-4fa5-4866-903e-9bf43aa27e0b', '80', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:03:00.000000'),

                                                                              ('m5n1a7d5-4fa5-4866-903e-9bf43aa27e0b', '85', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:04:00.000000'),
                                                                              ('o6p1a7d5-4fa5-4866-903e-9bf43aa27e0b', '90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:05:00.000000'),  ('q7r1a7d5-4fa5-4866-903e-9bf43aa27e0b', '95', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:06:00.000000'),  ('s8t1a7d5-4fa5-4866-903e-9bf43aa27e0b', '100', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:07:00.000000'),  ('u9v1a7d5-4fa5-4866-903e-9bf43aa27e0b', '55', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:08:00.000000'),  ('w0x1a7d5-4fa5-4866-903e-9bf43aa27e0b', '60', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 09:09:00.000000'),

                                                                              ('a0b1c2d3-e4f5-6g7h-8i9j-0k1l-2m3n-4o5p', '50', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:00:00.000000'),
                                                                              ('b1c2d3e4-f5g6-7h8i-9j0k-1l2m-3n4o-5p6q', '55', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:02:00.000000'),
                                                                              ('c2d3e4f5-g6h7-8i9j-0k1l-2m3n-4o5p-6q7r', '60', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:04:00.000000'),
                                                                              ('d3e4f5g6-h7i8-9j0k-1l2m-3n4o-5p6q-7r8s', '65', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:06:00.000000'),
                                                                              ('e4f5g6h7-i8j9-0k1l-2m3n-4o5p-6q7r-8s9t', '70', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:08:00.000000'),
                                                                              ('f5g6h7i8-j9k0-1l2m-3n4o-5p6q-7r8s-9t0u', '75', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:10:00.000000'),
                                                                              ('g6h7i8j9-k0l1-2m3n-4o5p-6q7r-8s9t-0u1v', '80', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:12:00.000000'),
                                                                              ('h7i8j9k0-l1m2-3n4o-5p6q-7r8s-9t0u-1v2w', '85', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:14:00.000000'),
                                                                              ('i8j9k0l1-m2n3-4o5p-6q7r-8s9t-0u1v-2w3x', '90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:16:00.000000'),
                                                                              ('j9k0l1m2-n3o4-5p6q-7r8s-9t0u-1v2w-3x4y', '95', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 10:18:00.000000'),

                                                                              ('h3i4j5k6-7l8m-9n0o-1p2q-3r4s-5t6u', '70', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 23:59:35.000000'),
                                                                              ('i4j5k6l7-8m9n-0o1p-2q3r-4s5t-6u7v', '75', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 23:59:40.000000'),
                                                                              ('j5k6l7m8-9n0o-1p2q-3r4s-5t6u-7v8w', '80', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 23:59:45.000000'),
                                                                              ('k6l7m8n9-0o1p-2q3r-4s5t-6u7v-8w9x', '85', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 23:59:50.000000'),
                                                                              ('l7m8n9o0-1p2q-3r4s-5t6u-7v8w-9x0y', '90', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-26 23:59:55.000000'),
                                                                              ('m8n9o0p1-2q3r-4s5t-6u7v-8w9x-0y1z', '95', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-27 00:00:00.000000'),
                                                                              ('n9o0p1q2-3r4s-5t6u-7v8w-9x0y-1z2a', '100', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-27 00:00:05.000000'),
                                                                              ('o0p1q2r3-4s5t-6u7v-8w9x-0y1z-2a3b', '105', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-27 00:00:10.000000'),
                                                                              ('p1q2r3s4-5t6u-7v8w-9x0y-1z2a-3b4c', '110', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-27 00:00:15.000000'),
                                                                              ('q2r3s4t5-6u7v-8w9x-0y1z-2a3b-4c5d', '115', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-27 00:00:20.000000'),
                                                                              ('r3s4t5u6-7v8w-9x0y-1z2a-3b4c-5d6e', '120', '2dc8bab9-7268-40ff-a980-2ce175587fd9', '2024-04-27 00:00:25.000000'),


-- sensor: 17080dc0-9213-4de8-9ba3-1917b3b16085 (SensorOfTemperature)

                                                                              ('d7a4b6c5-3e9d-4b5f-94b3-1a5f6e3b9d4c', '17.25', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:00.000000'),
                                                                              ('b5c7d6a4-4e9d-4b6f-94b3-1b5f6e3b9d4c', '18.15', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:06.000000'),
                                                                              ('a6d5c7b4-5e9d-4b7f-94b3-2b5f6e3b9d4c', '19.85', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:12.000000'),
                                                                              ('c7a5b6d4-6e9d-4b8f-94b3-2a5f6e3b9d4c', '16.40', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:18.000000'),
                                                                              ('b6d7c5a4-7e9d-4b9f-94b3-3b5f6e3b9d4c', '20.05', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:24.000000'),
                                                                              ('d6a4c5b7-8e9d-4b0f-94b3-3a5f6e3b9d4c', '21.50', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:30.000000'),
                                                                              ('e7a5b6d4-9e9d-4b1f-94b3-4b5f6e3b9d4c', '15.60', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:36.000000'),
                                                                              ('c5d7b6a4-0e9d-4b2f-94b3-4a5f6e3b9d4c', '22.35', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:42.000000'),
                                                                              ('d4a5b7c6-1e9d-4b3f-94b3-5b5f6e3b9d4c', '19.20', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:48.000000'),
                                                                              ('e5d7c4a6-2e9d-4b4f-94b3-5a5f6e3b9d4c', '18.75', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:00:54.000000'),

                                                                              ('2x0x26v9-q0vw-5h9k-9fq0-rg4w2v5w5tv1', '18.4', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:00.000000'),
                                                                              ('3y1y37w0-r1wx-5i0l-9fr0-sh5x3w6x6uv2', '19.1', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:06.000000'),
                                                                              ('4z2z48x1-s2xy-5j1m-9fs0-ti6y4x7y7wv3', '18.8', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:12.000000'),
                                                                              ('5a3a59y2-t3yz-5k2n-9ft0-uj7z5y8z8xw4', '18.9', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:18.000000'),
                                                                              ('6b4b60z3-u4za-5l3o-9fu0-vk8a6z9a9yx5', '19.3', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:24.000000'),
                                                                              ('7c5c71a4-v5ab-5m4p-9fv0-wl9b7a0b0zy6', '18.7', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:30.000000'),
                                                                              ('8d6d82b5-w6bc-5n5q-9fw0-xm0c8b1c1a07', '19.0', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:36.000000'),
                                                                              ('9e7e93c6-x7cd-5o6r-9fx0-yn1d9c2d2b18', '18.5', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:42.000000'),
                                                                              ('0f8f04d7-y8de-5p7s-9fy0-zo2e0d3e3c29', '19.2', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:48.000000'),
                                                                              ('1g9g15e8-z9ef-5q8t-9fz0-ap3f1e4f4d3a', '18.6', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 12:01:54.000000'),

                                                                              ('3gk94j94-ak2l-4hgg-9lk5-ml6j9539k6j4', '22.5', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:00.000000'),
                                                                              ('4hl05k05-bl3m-5ihh-0ml6-nm7k064a7k5', '21.8', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:06.000000'),
                                                                              ('5im16l16-cm4n-6jii-1nl7-om8l175b8l6', '23.2', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:12.000000'),
                                                                              ('6jn27m27-dn5o-7kkj-2om8-pn9m286c9m7', '22.0', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:18.000000'),
                                                                              ('7ko38n38-eo6p-8llk-3pn9-qo0n397d0n8', '23.8', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:24.000000'),
                                                                              ('8lp49o49-fp7q-9mmk-4qo0-rp1o408e1o9', '21.6', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:30.000000'),
                                                                              ('9mq50p50-gq8r-0nll-5rp1-sq2p519f2p0', '24.3', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:36.000000'),
                                                                              ('0nr61q61-hr9s-1oom-6sq2-tr3q62ag3q1', '20.9', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:42.000000'),
                                                                              ('1os72r72-is0t-2ppn-7tr3-us4r73bh4r2', '22.8', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:48.000000'),
                                                                              ('2pt83s83-jt1u-3qqo-8us4-vt5s84ci5s3', '23.5', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-25 13:00:54.000000'),

                                                                              ('5gl008a34-gl0o-3gl0-7ao4-go4g715h3g0g', '23.8', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:00.000000'),
                                                                              ('6hm119b45-hm1p-4hm1-8bp5-hp5h826i4h1h', '24.1', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:06.000000'),
                                                                              ('7in221c56-in2q-5in2-9cq6-iq6i937j5i2i', '23.7', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:12.000000'),
                                                                              ('8jo332d67-jo3r-6jo3-arq7-jr7j048k6j3j', '24.3', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:18.000000'),
                                                                              ('9kp443e78-kp4s-7kp4-bsr8-ks8k159l7k4k', '24.0', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:24.000000'),
                                                                              ('0lq554f89-lq5t-8lq5-ctr9-lt9l260m8l5l', '23.9', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:30.000000'),
                                                                              ('1mr665g90-mr6u-9mr6-dus0-mu0m371n9m6m', '24.5', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:36.000000'),
                                                                              ('3ot887b12-ot8w-1ot8-fwu2-ow2o593f1o8o', '23.6', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:42.000000'),
                                                                              ('4pu998c23-pu9x-2pu9-gxv3-px3p604g2p9p', '23.8', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:48.000000'),
                                                                              ('5qv109d34-qv1y-3qv1-hyw4-qy4q715h3q1q', '24.1', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 09:00:54.000000'),

                                                                              ('d7e8f9g0-0h1i-2j3k-4l5m-6n7o-8p9q-0r1s', '24', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:04:00.000000'),
                                                                              ('e8f9g0h1-1i2j-3k4l-5m6n-7o8p-9q0r-1s2t', '26', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:06:00.000000'),
                                                                              ('f9g0h1i2-2j3k-4l5m-6n7o-8p9q-0r1s-2t3u', '28', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:08:00.000000'),
                                                                              ('g0h1i2j3-3k4l-5m6n-7o8p-9q0r-1s2t-3u4v', '30', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:10:00.000000'),
                                                                              ('h1i2j3k4-4l5m-6n7o-8p9q-0r1s-2t3u-4v5w', '32', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:12:00.000000'),
                                                                              ('i2j3k4l5-5m6n-7o8p-9q0r-1s2t-3u4v-5w6x', '34', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:14:00.000000'),
                                                                              ('j3k4l5m6-6n7o-8p9q-0r1s-2t3u-4v5w-6x7y', '36', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:16:00.000000'),
                                                                              ('k4l5m6n7-7o8p-9q0r-1s2t-3u4v-5w6x-7y8z', '38', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:18:00.000000'),
                                                                              ('l5m6n7o8-8p9q-0r1s-2t3u-4v5w-6x7y-8z9a', '40', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:20:00.000000'),
                                                                              ('m6n7o8p9-9q0r-1s2t-3u4v-5w6x-7y8z-9a0b', '42', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 10:22:00.000000'),

                                                                              ('s4t5u6v7-8w9x-0y1z-2a3b-4c5d-6e7f', '20', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:31.000000'),
                                                                              ('v7w8x9y0-1z2a-3b4c-5d6e-7f8g-9h0i', '26', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:34.000000'),
                                                                              ('y0z1a2b3-4c5d-6e7f-8g9h-0i1j-2k3l', '32', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:37.000000'),
                                                                              ('c4d5e6f7-8g9h-0i1j-2k3l-4m5n-6o7p', '40', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:40.000000'),
                                                                              ('e6f7g8h9-0i1j-2k3l-4m5n-6o7p-8q9r', '44', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:42.000000'),
                                                                              ('h9i0j1k2-3l4m-5n6o-7p8q-9r0s-1t2u', '50', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:45.000000'),
                                                                              ('k2l3m4n5-6o7p-8q9r-0s1t-2u3v-4w5x', '56', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-26 23:59:48.000000'),
                                                                              ('x9y0z1a2-3b4c-5d6e-7f8g-9h0i-1j2k', '30', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-27 00:00:05.000000'),
                                                                              ('a2b3c4d5-6e7f-8g9h-0i1j-2k3l-4m5n', '36', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-27 00:00:08.000000'),
                                                                              ('u6v7w8x9-x0y1z-2a3b-4c5d-6e7f-8g9h', '24', '17080dc0-9213-4de8-9ba3-1917b3b16085', '2024-04-27 00:00:12.000000'),


-- sensor: 4187cfe0-34b6-4719-9811-847046b871cf (SensorOfTemperature)

                                                                              ('b6a4d5c7-3e9d-4b5f-94b3-6a5f6e3b9d4c', '17.90', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:00.000000'),
                                                                              ('d5c7a6b4-4e9d-4b6f-94b3-6b5f6e3b9d4c', '19.40', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:06.000000'),
                                                                              ('a4d6b7c5-5e9d-4b7f-94b3-7b5f6e3b9d4c', '21.10', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:12.000000'),
                                                                              ('c7b6a5d4-6e9d-4b8f-94b3-7a5f6e3b9d4c', '16.30', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:18.000000'),
                                                                              ('d5a4c6b7-7e9d-4b9f-94b3-8b5f6e3b9d4c', '22.20', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:24.000000'),
                                                                              ('e7b6c4a5-8e9d-4b0f-94b3-8a5f6e3b9d4c', '18.60', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:30.000000'),
                                                                              ('c5d6a7b4-9e9d-4b1f-94b3-9b5f6e3b9d4c', '20.70', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:36.000000'),
                                                                              ('d6b5a7c4-0e9d-4b2f-94b3-9a5f6e3b9d4c', '15.80', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:42.000000'),
                                                                              ('e5c7b4a6-1e9d-4b3f-94b3-0a5f6e3b9d4c', '19.55', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:48.000000'),
                                                                              ('a6d7c4b5-2e9d-4b4f-94b3-0b5f6e3b9d4c', '21.35', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:00:54.000000'),

                                                                              ('2h0h26f9-a0gb-5r9u-9gq0-bh4g2f5f5ua1', '21.3', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:00.000000'),
                                                                              ('3i1i37g0-b1hc-5s0v-9hr0-ci5h3g6g6vb2', '20.7', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:06.000000'),
                                                                              ('4j2j48h1-c2id-5t1w-9is0-dj6i4h7h7wc3', '21.1', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:12.000000'),
                                                                              ('5k3k59i2-d3je-5u2x-9jt0-ek7j5i8i8xd4', '21.5', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:18.000000'),
                                                                              ('6l4l60j3-e4kf-5v3y-9ku0-fl8k6j9j9ye5', '20.9', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:24.000000'),
                                                                              ('7m5m71k4-f5lg-5w4z-9lv0-gl9l7k0k0zf6', '21.2', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:30.000000'),
                                                                              ('8n6n82l5-g6mh-5x5a-9mw0-hm0m8l1l1ag7', '21.0', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:36.000000'),
                                                                              ('9o7o93m6-h7ni-5y6b-9nx0-in1n9m2m2bh8', '20.6', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:42.000000'),
                                                                              ('0p8p04n7-i8oj-5z7c-9oy0-jo2o0n3n3ci9', '21.4', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:48.000000'),
                                                                              ('1q9q15o8-j9pk-509d-9pz0-kp3p1o4o4dj0', '20.8', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 12:01:54.000000'),

                                                                              ('3qu94t94-aq2u-4rrp-9vt5-wu6t9539t6t4', '20.8', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:00.000000'),
                                                                              ('4rv05u05-br3v-5ssp-0wu6-xv7u064a7u5', '19.5', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:06.000000'),
                                                                              ('5sw16v16-cs4w-6ttq-1xu7-yw8v175b8v6', '21.0', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:12.000000'),
                                                                              ('6tx27w27-du5x-7uus-2yv8-zx9w286c9w7', '19.2', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:18.000000'),
                                                                              ('7uy38x38-ev6y-8vvt-3zw9-ay0x397d0x8', '20.7', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:24.000000'),
                                                                              ('8vz49y49-fw7z-9wwu-4ax0-bz1y408e1y9', '21.8', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:30.000000'),
                                                                              ('9w105z50-gx8a-0xxt-5by1-cz2z519f2z0', '20.5', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:36.000000'),
                                                                              ('0xa61az61-hy9b-1yys-6cz2-dy3a62ag3a1', '19.9', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:42.000000'),
                                                                              ('1yb72b72-iz0c-2zzt-7d1a-ez4b73bh4b2', '21.3', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:48.000000'),
                                                                              ('2zc83c83-jz1d-3aaq-8e2b-fa5c84ci5c3', '20.2', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-25 13:00:54.000000'),

                                                                              ('6rw221e56-rw2z-4rw2-7ez5-wz5w826i4w2w', '21.3', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:00.000000'),
                                                                              ('7sx332f67-sx3a-5sx3-fxa6-xa6x048k6x3x', '21.5', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:06.000000'),
                                                                              ('8ty443g78-ty4b-6ty4-gyb7-yb7y159l7y4y', '21.7', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:12.000000'),
                                                                              ('9uz554h89-uz5c-7uz5-hzc8-zc8z260m8z5z', '21.9', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:18.000000'),
                                                                              ('0v1655i90-v16d-8v16-id9d-1d9d371n9d6d', '22.1', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:24.000000'),
                                                                              ('1w2766j01-w27e-9w27-jfa0-ea0e482e1e7e', '22.3', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:30.000000'),
                                                                              ('2x3877k12-x38f-0x38-kgb1-fb1f593f1f8f', '22.5', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:36.000000'),
                                                                              ('3y4988l23-y39g-1y39-lhc2-gc2g604g2g9g', '22.7', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:42.000000'),
                                                                              ('4z5099m34-z40h-2z40-mid3-hd3h715h3h0h', '22.9', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:48.000000'),
                                                                              ('n7o8p9q0-0r1s-2t3u-4v5w-6x7y-8z9a-0b1c', '20', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 09:00:55.000000'),

                                                                              ('p9q0r1s2-3t4u-5v6w-7x8y-9z0a-b1c2d3e', '24', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:04:00.000000'),
                                                                              ('q0r1s2t3-4u5v-6w7x-8y9z-0a1b-c2d3e4f', '26', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:06:00.000000'),
                                                                              ('r1s2t3u4-5v6w-7x8y-9z0a-1b2c-d3e4f5g', '28', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:08:00.000000'),
                                                                              ('s2t3u4v5-6w7x-8y9z-0a1b-2c3d-4e5f-6g7h', '30', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:10:00.000000'),
                                                                              ('t3u4v5w6-7x8y-9z0a-1b2c-3d4e-5f6g-7h8i', '32', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:12:00.000000'),
                                                                              ('u4v5w6x7-8y9z-0a1b-2c3d-4e5f-6g7h-8i9j', '34', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:14:00.000000'),
                                                                              ('v5w6x7y8-9z0a-1b2c-3d4e-5f6g-7h8i-9j0k', '36', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:16:00.000000'),
                                                                              ('w6x7y8z9-0a1b-2c3d-4e5f-6g7h-8i9j-0k1l', '38', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:18:00.000000'),
                                                                              ('x7y8z9a0-1b2c-3d4e-5f6g-7h8i-9j0k-1l2m', '40', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:20:00.000000'),
                                                                              ('y8z9a0b1-2c3d-4e5f-6g7h-8i9j-0k1l-2m3n', '42', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 10:22:00.000000'),

                                                                              ('m4n5o6p7-8q9r-0s1t-2u3v-4w5x-6y7z', '21', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 23:59:31.000000'),
                                                                              ('q8r9s0t1-2u3v-4w5x-6y7z-8a9b-0c1d', '25', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 23:59:35.000000'),
                                                                              ('u2v3w4x5-6y7z-8a9b-0c1d-2e3f-4g5h', '29', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 23:59:39.000000'),
                                                                              ('o6p7q8r9-0s1t-2u3v-4w5x-6y7z-8a9b', '23', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 23:59:43.000000'),
                                                                              ('s0t1u2v3-4w5x-6y7z-8a9b-0c1d-2e3f', '27', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 23:59:47.000000'),
                                                                              ('n5o6p7q8-9r0s-1t2u-3v4w-5x6y-7z8a', '22', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-26 23:59:52.000000'),
                                                                              ('p7q8r9s0-1t2u-3v4w-5x6y-7z8a-9b0c', '24', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-27 00:00:04.000000'),
                                                                              ('t1u2v3w4-5x6y-7z8a-9b0c-1d2e-3f4g', '28', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-27 00:00:08.000000'),
                                                                              ('r9s0t1u2-3v4w-5x6y-7z8a-9b0c-1d2e', '26', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-27 00:00:16.000000'),
                                                                              ('v3w4x5y6-7z8a-9b0c-1d2e-3f4g-5h6i', '30', '4187cfe0-34b6-4719-9811-847046b871cf', '2024-04-27 00:00:20.000000'),


-- sensor: 37953320-0cbb-44fe-9d9a-534c5212dc7f (SensorOfPowerConsumption)

                                                                              ('f4d7c3b5-3e9d-4b5f-94b3-1a5f6e3b9d4c', '2964.15', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:00.000000'),
                                                                              ('d7b6a5c4-4e9d-4b6f-94b3-1b5f6e3b9d4c', '3142.50', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:06.000000'),
                                                                              ('c5a4b6d7-5e9d-4b7f-94b3-2b5f6e3b9d4c', '2456.35', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:12.000000'),
                                                                              ('d6c7b5a4-6e9d-4b8f-94b3-2a5f6e3b9d4c', '4265.70', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:18.000000'),
                                                                              ('e7d6a5b4-7e9d-4b9f-94b3-3b5f6e3b9d4c', '1536.25', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:24.000000'),
                                                                              ('c4a7d5b6-8e9d-4b0f-94b3-3a5f6e3b9d4c', '3284.60', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:30.000000'),
                                                                              ('d7a5b6c4-9e9d-4b1f-94b3-4b5f6e3b9d4c', '3891.20', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:36.000000'),
                                                                              ('e6b5d7a4-0e9d-4b2f-94b3-4a5f6e3b9d4c', '2874.75', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:42.000000'),
                                                                              ('b4c7d5a6-1e9d-4b3f-94b3-5b5f6e3b9d4c', '2148.50', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:48.000000'),
                                                                              ('c6d7a5b4-2e9d-4b4f-94b3-5a5f6e3b9d4c', '3025.85', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:00:54.000000'),

                                                                              ('2r0r26p9-k0qb-509e-9qr0-lh4q2p5q5ua1', '268.55', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:00.000000'),
                                                                              ('3s1s37q0-l1rc-519f-9rs0-mi5r3q6r6vb2', '280.27', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:06.000000'),
                                                                              ('4t2t48r1-m2sd-529g-9ts0-nj6s4r7s7wc3', '292.18', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:12.000000'),
                                                                              ('5u3u59s2-n3te-539h-9us0-ok7t5s8t8xd4', '276.45', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:18.000000'),
                                                                              ('6v4v60t3-o4uf-549i-9vs0-pl8u6t9u9ye5', '299.12', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:24.000000'),
                                                                              ('7w5w71u4-p5vg-559j-9ws0-qm9v7u0v0zf6', '271.34', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:30.000000'),
                                                                              ('8x6x82v5-q6wh-569k-9xs0-rn0w8v1w1ag7', '286.29', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:36.000000'),
                                                                              ('9y7y93w6-r7xi-579l-9ys0-so1x9w2x2bh8', '294.31', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:42.000000'),
                                                                              ('0z8z04x7-s8yj-589m-9zs0-to2y0x3y3ci9', '273.68', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:48.000000'),
                                                                              ('1a9a15y8-t9zk-599n-9zs0-up3z1y4z4dj0', '281.77', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 12:01:54.000000'),

                                                                              ('3ad94d94-bd2e-4ddr-9ee5-ff6d9539d6d4', '130', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:00.000000'),
                                                                              ('4be05e05-ce3f-5eess-0ff6-gg7e064a7e5', '128', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:06.000000'),
                                                                              ('5cf16f16-df4g-6ffr-1gg7-hh8f175b8f6', '135', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:12.000000'),
                                                                              ('6dg27g27-eg5h-7ggs-2hh8-ii9g286c9g7', '133', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:18.000000'),
                                                                              ('7eh38h38-fh6i-8hhq-3ii9-jj0h397d0h8', '129', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:24.000000'),
                                                                              ('8fi49i49-gi7j-9iiu-4jj0-kk1i408e1i9', '138', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:30.000000'),
                                                                              ('9gj50j50-hj8k-0jjr-5kk1-ll2j519f2j0', '132', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:36.000000'),
                                                                              ('0hk61k61-ik9l-1kkk-6ll2-mm3k62ag3k1', '134', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:42.000000'),
                                                                              ('1il72l72-jl0m-2lll-7mm3-nn4l73bh4l2', '136', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:48.000000'),
                                                                              ('2jm83m83-km1n-3mmm-8nn4-oo5m84ci5m3', '131', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-25 13:00:54.000000'),

                                                                              ('5a60abf7-39a7-4ba3-a2c0-26cd6db7e492', '320', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:00.000000'),  ('3d2016a3-fc3f-4788-97e9-2ab944784c35', '315', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:06.000000'),  ('a96b459a-3d8a-4d46-9325-b9b8f29f24ee', '310', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:12.000000'),  ('b87bc8b8-8570-474f-bf46-33f7f560949f', '305', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:18.000000'),  ('c13cd05e-8cc9-44b2-a290-5c0d4fc1a804', '300', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:24.000000'),  ('38c5f203-4e28-4e4f-90e2-df47f757761f', '295', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:30.000000'),  ('e8f87272-d1d3-4092-a689-c9b34c59bb29', '290', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:36.000000'),  ('6c0022a4-b84b-4a88-99f1-4b1e4566189a', '285', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:42.000000'),  ('fa68d7cd-8dc2-4485-95a1-75942c37a228', '280', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:48.000000'),  ('af22aaf4-2d67-4649-9b63-733d8f8a70a9', '275', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 09:00:54.000000'),

                                                                              ('4f5g6h7i-8j9k-0l1m-2n3o-4p5q-6r7s-8t9u', '1000', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:00:00.000000'),
                                                                              ('5g6h7i8j-9k0l-1m2n-3o4p-5q6r-7s8t-9u0v', '1010', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:02:00.000000'),
                                                                              ('6h7i8j9k-0l1m-2n3o-4p5q-6r7s-8t9u-0v1w', '1020', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:04:00.000000'),
                                                                              ('7i8j9k0l-1m2n-3o4p-5q6r-7s8t-9u0v-1w2x', '1030', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:06:00.000000'),
                                                                              ('8j9k0l1m-2n3o-4p5q-6r7s-8t9u-0v1w-2x3y', '1040', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:08:00.000000'),
                                                                              ('9k0l1m2n-3o4p-5q6r-7s8t-9u0v-1w2x-3y4z', '1050', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:10:00.000000'),
                                                                              ('0l1m2n3o-4p5q-6r7s-8t9u-0v1w-2x3y-4z5a', '1060', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:12:00.000000'),
                                                                              ('1m2n3o4p-5q6r-7s8t-9u0v-1w2x-3y4z-5a6b', '1070', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:14:00.000000'),
                                                                              ('2n3o4p5q-6r7s-8t9u-0v1w-2x3y-4z5a-6b7c', '1080', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:16:00.000000'),
                                                                              ('3o4p5q6r-7s8t-9u0v-1w2x-3y4z-5a6b-7c8d', '1090', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 10:18:00.000000'),

                                                                              ('z7a8b9c0-1d2e-3f4g-5h6i-7j8k-9l0m', '195', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:34.000000'),
                                                                              ('c0d1e2f3-4g5h-6i7j-8k9l-0m1n-2o3p', '210', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:37.000000'),
                                                                              ('f3g4h5i6-7j8k-9l0m-1n2o-3p4q-5r6s', '225', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:40.000000'),
                                                                              ('i6j7k8l9-0m1n-2o3p-4q5r-6s7t-8u9v', '240', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:43.000000'),
                                                                              ('m0n1o2p3-4q5r-6s7t-8u9v-0w1x-2y3z', '260', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:47.000000'),
                                                                              ('a8b9c0d1-2e3f-4g5h-6i7j-8k9l-0m1n', '200', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:55.000000'),
                                                                              ('e2f3g4h5-6i7j-8k9l-0m1n-2o3p-4q5r', '220', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-26 23:59:59.000000'),
                                                                              ('h5i6j7k8-9l0m-1n2o-3p4q-5r6s-7t8u', '235', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:22.000000'),
                                                                              ('l9m0n1o2-3p4q-5r6s-7t8u-9v0w-1x2y', '255', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:06.000000'),
                                                                              ('g4h5i6j7-8k9l-0m1n-2o3p-4q5r-6s7t', '230', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:11.000000'),
                                                                              ('j7k8l9m0-1n2o-3p4q-5r6s-7t8u-9v0w', '245', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:14.000000'),
                                                                              ('b9c0d1e2-3f4g-5h6i-7j8k-9l0m-1n2o', '205', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:16.000000'),
                                                                              ('d1e2f3g4-5h6i-7j8k-9l0m-1n2o-3p4q', '215', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:21.000000'),
                                                                              ('k8l9m0n1-2o3p-4q5r-6s7t-8u9v-0w1x', '250', '37953320-0cbb-44fe-9d9a-534c5212dc7f', '2024-04-27 00:00:25.000000');


