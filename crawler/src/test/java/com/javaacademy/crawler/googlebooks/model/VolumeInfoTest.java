package com.javaacademy.crawler.googlebooks.model;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class VolumeInfoTest {

    public void testGetters() {
        VolumeInfo volumeInfo = new VolumeInfo();
        String dummyString = "Dummy";
        volumeInfo.title = dummyString;
        volumeInfo.subtitle = dummyString;
        volumeInfo.canonicalVolumeLink = dummyString;
        List<String> dummyList = new ArrayList<>(Arrays.asList("FirstElement", "SecondElement"));
        volumeInfo.authors = dummyList;
        volumeInfo.categories = dummyList;
        List<Isbn> isbns = new ArrayList<>();
        volumeInfo.industryIdentifiers = isbns;

        assertEquals(volumeInfo.getTitle(), dummyString);
        assertEquals(volumeInfo.getSubtitle(), dummyString);
        assertEquals(volumeInfo.getCanonicalVolumeLink(), dummyString);
        assertEquals(volumeInfo.getAuthors(), dummyList);
        assertEquals(volumeInfo.getCategories(), dummyList);
        assertEquals(volumeInfo.getIndustryIdentifiers(), isbns);
    }

    public void testEquals() {
        VolumeInfo volumeInfo = new VolumeInfo();
        VolumeInfo volumeInfo1 = new VolumeInfo();
        assertTrue(volumeInfo.equals(volumeInfo1));
        volumeInfo.title = "dummyTitle";
        assertFalse(volumeInfo.equals(volumeInfo1));
    }

    public void testToString() {
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.title = "Dummy";
        volumeInfo.industryIdentifiers = new ArrayList<>();
        assertEquals(volumeInfo.toString(), "VolumeInfo(title=Dummy, subtitle=null, authors=null, industryIdentifiers=[], categories=null, imageLinks=null, canonicalVolumeLink=null)");
    }

}