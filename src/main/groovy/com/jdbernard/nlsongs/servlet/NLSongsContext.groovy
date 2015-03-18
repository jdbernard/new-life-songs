package com.jdbernard.nlsongs.servlet

import com.jdbernard.nlsongs.db.NLSongsDB
import com.jdbernard.nlsongs.model.Service
import com.jdbernard.nlsongs.model.Song

public class NLSongsContext {

    public static NLSongsDB songsDB
    
    public static String mediaUrlBase

    public static String makeUrl(Service service, Song song) {
        return mediaUrlBase + service.@date.toString('yyyy-MM-dd') +
            '/' + song.name.replaceAll(/\s/, '') + '.ogg' }
}
