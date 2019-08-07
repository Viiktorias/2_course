<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
    <xsl:template match="/">
            Список игроков

<xsl:for-each select="players/player"><xsl:value-of select="concat('• ',@id, ', ', @surname, ', ', @name, ', ', @patronymic, ', ', team, ', ', rating, '.&#13;')"/>
        </xsl:for-each>
            <xsl:value-of select="concat('&#13;', 'Всего игроков: ', count(players/player), '&#13;')"/>
            <xsl:value-of select="concat('Средний рейтинг: ', sum(players/player/rating) div count(players/player/rating), '&#13;')"/>
    </xsl:template>
</xsl:stylesheet>
