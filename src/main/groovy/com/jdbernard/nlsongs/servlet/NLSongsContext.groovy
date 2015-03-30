package com.jdbernard.nlsongs.servlet

import com.jdbernard.nlsongs.db.NLSongsDB
import com.jdbernard.nlsongs.model.Service
import com.jdbernard.nlsongs.model.Song

public class NLSongsContext {

    public static NLSongsDB songsDB
    
    public static String mediaBaseUrl

    public static String makeUrl(Service service, Song song) {
        return mediaBaseUrl + '/' + service.@date.toString('yyyy-MM-dd') + '_' +
            service.serviceType.name().toLowerCase() + '_' +
            song.name.replaceAll(/[\s'"\\\/\?!]/, '') + '.mp3' }
}
