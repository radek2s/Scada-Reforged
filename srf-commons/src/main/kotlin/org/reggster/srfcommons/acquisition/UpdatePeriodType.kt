package org.reggster.srfcommons.acquisition

enum class TimePeriod(val multiplier: Int) {
    SECONDS(1),
    MINUTES(60),
    HOURS(3600),
    DAYS(86400),
    WEEKS(604800),
    MONTHS(18144000),
    YEARS(217728000);
}