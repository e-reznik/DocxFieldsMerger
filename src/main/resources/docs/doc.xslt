<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
    <xsl:output indent="yes" method="xml" encoding="utf-8" />

    <xsl:param name="firstName" />
    <xsl:param name="lastName" />
    <xsl:param name="street" />
    <xsl:param name="houseNo" />
    <xsl:param name="city" />
    <xsl:param name="state" />
    <xsl:param name="zip" />
    
    <xsl:param name="subject" />
    <xsl:param name="date" />
    
    <xsl:param name="person1" />
    <xsl:param name="noun1" />
    <xsl:param name="person2" />
    <xsl:param name="verb" />
    <xsl:param name="noun2" />

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*" />
        </xsl:copy>
    </xsl:template>
      
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD firstName '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD firstName ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD lastName '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD lastName ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD street '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD street ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD houseNo '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD houseNo ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD state '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD state ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD city '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD city ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD zip '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD zip ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD subject '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD subject ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD date '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD date ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD person1 '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD person1 ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD noun1 '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD noun1 ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD person2 '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD person2 ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD verb '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD verb ']]"/> 
    <xsl:template match="*[following-sibling::*[1]/*[text()=' MERGEFIELD noun2 '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD noun2 ']]"/> 

    <xsl:template match="w:instrText[text()=' MERGEFIELD firstName ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$firstName" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD lastName ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$lastName" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD street ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$street" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD houseNo ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$houseNo" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD city ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$city" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD state ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$state" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD zip ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$zip" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD subject ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$subject" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD date ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$date" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD person1 ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$person1" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD noun1 ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$noun1" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD person2 ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$person2" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD verb ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$verb" />
        </xsl:element>  
    </xsl:template>
    <xsl:template match="w:instrText[text()=' MERGEFIELD noun2 ']"> 
        <xsl:element name="w:t">
            <xsl:value-of select="$noun2" />
        </xsl:element>  
    </xsl:template>
</xsl:stylesheet>