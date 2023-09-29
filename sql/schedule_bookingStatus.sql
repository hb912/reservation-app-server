CREATE EVENT IF NOT EXISTS autoSetStatus
ON SCHEDULE every 1 day
STARTS '2023-09-12'
ON COMPLETION PRESERVE
  COMMENT 'set booking status booking_EXP if end Date before today'
DO
  UPDATE dingul_camping.booking
  SET status="BOOKING_EXP"
  WHERE end_date<NOW()