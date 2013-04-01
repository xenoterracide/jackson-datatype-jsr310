/*
 * Copyright 2013 FasterXML.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;

import static org.junit.Assert.*;

public class TestOffsetTimeSerialization
{
    private ObjectMapper mapper;

    @Before
    public void setUp()
    {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testSerializationAsTimestamp01() throws Exception
    {
        OffsetTime time = OffsetTime.of(15, 43, 0, 0, ZoneOffset.of("+0300"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "[15,43,\"+03:00\"]", value);
    }

    @Test
    public void testSerializationAsTimestamp02() throws Exception
    {
        OffsetTime time = OffsetTime.of(9, 22, 57, 0, ZoneOffset.of("-0630"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "[9,22,57,\"-06:30\"]", value);
    }

    @Test
    public void testSerializationAsTimestamp03() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "[22,31,5,829837,\"+11:00\"]", value);
    }

    @Test
    public void testSerializationAsString01() throws Exception
    {
        OffsetTime time = OffsetTime.of(15, 43, 0, 0, ZoneOffset.of("+0300"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + time.toString() + '"', value);
    }

    @Test
    public void testSerializationAsString02() throws Exception
    {
        OffsetTime time = OffsetTime.of(9, 22, 57, 0, ZoneOffset.of("-0630"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + time.toString() + '"', value);
    }

    @Test
    public void testSerializationAsString03() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + time.toString() + '"', value);
    }

    @Test
    public void testSerializationWithTypeInfo01() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + OffsetTime.class.getName() + "\",[22,31,5,829837,\"+11:00\"]]", value);
    }

    @Test
    public void testSerializationWithTypeInfo02() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(time);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + OffsetTime.class.getName() + "\",\"" + time.toString() + "\"]", value);
    }

    @Test
    public void testDeserializationAsTimestamp01() throws Exception
    {
        OffsetTime time = OffsetTime.of(15, 43, 0, 0, ZoneOffset.of("+0300"));

        OffsetTime value = this.mapper.readValue("[15,43,\"+0300\"]", OffsetTime.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationAsTimestamp02() throws Exception
    {
        OffsetTime time = OffsetTime.of(9, 22, 57, 0, ZoneOffset.of("-0630"));

        OffsetTime value = this.mapper.readValue("[9,22,57,\"-06:30\"]", OffsetTime.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationAsTimestamp03() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        OffsetTime value = this.mapper.readValue("[22,31,5,829837,\"+11:00\"]", OffsetTime.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        OffsetTime time = OffsetTime.of(15, 43, 0, 0, ZoneOffset.of("+0300"));

        OffsetTime value = this.mapper.readValue('"' + time.toString() + '"', OffsetTime.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationAsString02() throws Exception
    {
        OffsetTime time = OffsetTime.of(9, 22, 57, 0, ZoneOffset.of("-0630"));

        OffsetTime value = this.mapper.readValue('"' + time.toString() + '"', OffsetTime.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationAsString03() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        OffsetTime value = this.mapper.readValue('"' + time.toString() + '"', OffsetTime.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationWithTypeInfo01() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue(
                "[\"" + OffsetTime.class.getName() + "\",[22,31,5,829837,\"+11:00\"]]", Temporal.class
        );

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a OffsetTime.", value instanceof OffsetTime);
        assertEquals("The value is not correct.", time, value);
    }

    @Test
    public void testDeserializationWithTypeInfo02() throws Exception
    {
        OffsetTime time = OffsetTime.of(22, 31, 5, 829837, ZoneOffset.of("+1100"));

        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue(
                "[\"" + OffsetTime.class.getName() + "\",\"" + time.toString() + "\"]", Temporal.class
        );

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a OffsetTime.", value instanceof OffsetTime);
        assertEquals("The value is not correct.", time, value);
    }
}