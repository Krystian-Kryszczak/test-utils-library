/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.fixture

import com.datastax.oss.driver.api.core.uuid.Uuids
import krystian.kryszczak.commons.extenstion.datetime.toDateInDays
import krystian.kryszczak.commons.model.being.user.User
import java.time.LocalDate

private val johnSmithUserId = Uuids.startOf(System.currentTimeMillis() + 100)
private val jackSmithUserId = Uuids.startOf(System.currentTimeMillis() + 250)
private val jimSmithUserId = Uuids.startOf(System.currentTimeMillis() + 450)
private val jasonSmithUserId = Uuids.startOf(System.currentTimeMillis() + 500)

val johnSmithUser = User(
    johnSmithUserId,
    "John",
    "Smith",
    "john.smith@example.com",
    "555 555 555",
    LocalDate.of(2003, 7, 25).toDateInDays(),
    mutableSetOf(jackSmithUserId),
    1,
    null
)

val jackSmithUser = User(
    jackSmithUserId,
    "Jack",
    "Smith",
    "jack.smith@example.com",
    "585 585 585",
    LocalDate.of(2003, 8, 20).toDateInDays(),
    mutableSetOf(johnSmithUserId, jasonSmithUserId),
    1,
    null
)

val jimSmithUser = User(
    jimSmithUserId,
    "Jim",
    "Smith",
    "jim.smith@example.com",
    "785 785 785",
    LocalDate.of(2003, 4, 15).toDateInDays(),
    mutableSetOf(jackSmithUserId),
    1,
    null
)

val jasonSmithUser = User(
    jasonSmithUserId,
    "Jason",
    "Smith",
    "jason.smith@example.com",
    "885 885 885",
    LocalDate.of(2003, 6, 10).toDateInDays(),
    mutableSetOf(),
    1,
    null
)
