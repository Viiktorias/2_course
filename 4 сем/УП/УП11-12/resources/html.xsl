<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:variable name="apos">'</xsl:variable>
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"></meta>
                <title>Список игроков</title>
                <link rel="shortcut icon" href="https://rating.chgk.info/favicon.yellow.ico"></link>
                <style>
                    body {
                    background: #f4f0cb;
                    }
                    table {
                    color: #271d0c;
                    width: 95%;
                    font-size: 16px;
                    margin: 20px;
                    border: #271d0c 1px solid;
                    border-radius: 3px;
                    box-shadow: 0 1px 2px #5f461b;
                    }

                    th {
                    padding: 21px 25px 22px 25px;
                    background: #f4f0cb;
                    text-align: center
                    }

                    tr {
                    text-align: center;
                    padding-left: 20px;
                    }

                    td {
                    padding: 18px;
                    border-left: 1px solid #f4f0cb;
                    border-right: 1px solid #f4f0cb;
                    }

                    tr, th {
                    overflow-wrap: break-word;
                    word-wrap: break-word;
                    }

                    tr:nth-child(even) {
                    background-color: #f4f0cb
                    }

                    tr:nth-child(odd) {
                    background-color: #ded29e
                    }
                </style>
            </head>
            <body>
                <table>
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Фамилия</th>
                            <th scope="col">Имя</th>
                            <th scope="col">Отчество</th>
                            <th scope="col">Команда</th>
                            <th scope="col">Рейтинг</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="players/player">
                            <tr>
                                <td>
                                    <xsl:value-of select="@id"/>
                                </td>
                                <td>
                                    <xsl:value-of select="@surname"/>
                                </td>
                                <td>
                                    <xsl:value-of select="@name"/>
                                </td>
                                <td>
                                    <xsl:value-of select="@patronymic"/>
                                </td>
                                <td>
                                    <xsl:value-of select="team"/>
                                </td>
                                <td>
                                    <xsl:value-of select="rating"/>
                                </td>
                            </tr>
                        </xsl:for-each>

                        <tr>
                            <td colspan="5">
                                <strong>Всего игроков:</strong>
                            </td>
                            <td>
                                <xsl:value-of select="count(players/player)"/>
                            </td>
                        </tr>
                        <tr>

                            <td colspan="5">
                                <strong>Средний рейтинг:</strong>
                            </td>
                            <td>
                                <xsl:value-of select="sum(players/player/rating) div count(players/player)"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>