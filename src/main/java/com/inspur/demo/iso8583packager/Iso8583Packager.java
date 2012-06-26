package com.inspur.demo.iso8583packager;

import com.inspur.demo.logger.I18nLoggerFactory;
import static com.inspur.demo.message.ExceptionResources.CONF_NOT_FOUND_ISO_PACKAGER;
import com.inspur.demo.message.LogResources;
import com.inspur.demo.message.MessageFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.cal10n.LocLogger;

public class Iso8583Packager {
    
    private final LocLogger logger = I18nLoggerFactory.getLogger(getClass());
    
    private ISOBasePackager packager;

    public Iso8583Packager(final String configFile) throws IOException, ISOException {
        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
        if (is == null) {
            final String errorMsg = MessageFactory.getMessage(CONF_NOT_FOUND_ISO_PACKAGER, configFile);
            logger.error(errorMsg);
            throw new IOException(errorMsg);
        }
        try {
            packager = new GenericPackager(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public Map<Object,Object> unpack(final byte[] message) throws ISOException {
        logger.info(LogResources.UNPACK_BEGIN);
        final ISOMsg isoMsg = new ISOMsg();
        packager.unpack(isoMsg, message);
        return isoMsg.getChildren();
    }
    
    public byte[] pack(Map<Object,Object> msgObj) throws ISOException {
        logger.info(LogResources.PACK_BEGIN);
        final ISOMsg msg = new ISOMsg();
        for (final Object bit : msgObj.keySet()) {
            msg.set((ISOComponent) msgObj.get(bit));
        }
        return packager.pack(msg);
    }
}
