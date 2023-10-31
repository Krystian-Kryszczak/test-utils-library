/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.fixture

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.collections.shouldNotContainDuplicates
import io.kotest.matchers.collections.shouldNotContainNull

class UserTestFixturesTest : FreeSpec({
    "user test fixtures users ids" - {
        "should not contain null and duplicates" {
            arrayOf(
                johnSmithUser.id,
                jackSmithUser.id,
                jimSmithUser.id,
                jasonSmithUser.id,
                lilySmithUser.id
            ).shouldNotContainNull()
            .shouldNotContainDuplicates()
        }
    }

    "user test fixtures users names" - {
        "should not contain null and duplicates" {
            arrayOf(
                johnSmithUser.name,
                jackSmithUser.name,
                jimSmithUser.name,
                jasonSmithUser.name,
                lilySmithUser.name
            ).shouldNotContainNull()
            .shouldNotContainDuplicates()
        }
    }

    "user test fixtures users emails" - {
        "should not contain null and duplicates" {
            arrayOf(
                johnSmithUser.email,
                jackSmithUser.email,
                jimSmithUser.email,
                jasonSmithUser.email,
                lilySmithUser.email
            ).shouldNotContainNull()
            .shouldNotContainDuplicates()
        }
    }

    "user test fixtures users phone numbers" - {
        "should not contain null and duplicates" {
            arrayOf(
                johnSmithUser.phoneNumber,
                jackSmithUser.phoneNumber,
                jimSmithUser.phoneNumber,
                jasonSmithUser.phoneNumber,
                lilySmithUser.phoneNumber
            ).shouldNotContainNull()
            .shouldNotContainDuplicates()
        }
    }

    "user test fixtures users dates of birth" - {
        "should not contain null and duplicates" {
            arrayOf(
                johnSmithUser.dateOfBirthInDays,
                jackSmithUser.dateOfBirthInDays,
                jimSmithUser.dateOfBirthInDays,
                jasonSmithUser.dateOfBirthInDays,
                lilySmithUser.dateOfBirthInDays
            ).shouldNotContainNull()
            .shouldNotContainDuplicates()
        }
    }

    "user test fixtures users friends lists" - {
        "should not contain null and duplicates" {
            arrayOf(
                johnSmithUser.friends,
                jackSmithUser.friends,
                jimSmithUser.friends,
                jasonSmithUser.friends,
                lilySmithUser.friends
            ).shouldNotContainNull()
            .shouldNotContainDuplicates()
        }
    }

    "user test fixtures users sex" - {
        "should not contain 0 value and contain 1 and 2" {
            arrayOf(
                johnSmithUser.sex,
                jackSmithUser.sex,
                jimSmithUser.sex,
                jasonSmithUser.sex,
                lilySmithUser.sex
            ).shouldNotContain(0)
            .shouldContain(1).shouldContain(2)
        }
    }
})
