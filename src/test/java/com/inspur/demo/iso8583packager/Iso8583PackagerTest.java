package com.inspur.demo.iso8583packager;

import com.inspur.demo.logger.I18nLoggerFactory;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.slf4j.cal10n.LocLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Iso8583PackagerTest {
    
    private LocLogger logger = I18nLoggerFactory.getLogger(getClass());
    
    private Iso8583Packager target;
    
    @BeforeTest
    public void beforeTest() throws IOException, ISOException {
        target = new Iso8583Packager("config/iso-packager.xml");
    }
    
    @Test(expectedExceptions = { IOException.class })
    public void loadConfigTest() throws IOException, ISOException {
        target = new Iso8583Packager("dummy-file");
    }

    @Test(dependsOnMethods = "loadConfigTest")
    public void unpackTest() throws ISOException {
        final Map<Object, Object> result = target.unpack(Iso8583TestData.getMessage(0, false));
        for (final Map.Entry<Object, Object> item : result.entrySet()) {
            final Object value = item.getValue();
            final String valueLiteral = literal(value);
            logger.info("Field {}: {}", item.getKey(), valueLiteral);
        }
    }
    
    @Test(dependsOnMethods = "unpackTest")
    public void packTest() throws ISOException {
        final byte[] msgBefore = Iso8583TestData.getMessage(0, false);
        final Map<Object, Object> result = target.unpack(msgBefore);
        final byte[] msg = target.pack(result);
        logger.info("Before pack: {}", msgBefore);
        logger.info("After pack:  {}", msg);
        Assert.assertEquals(msg, msgBefore);
    }
    
    private String literal(Object obj) throws ISOException {
        if (obj instanceof ISOComponent) {
            return literal(((ISOComponent) obj).getValue());
        }
        
        if (obj instanceof String) {
            return "\"" + obj + "\"";
        }
        if (obj instanceof Character) {
            return "'" + obj + "'";
        }
        if (obj instanceof byte[]) {
            final byte[] bytes = (byte[]) obj;
            final String[] hex = new String[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                hex[i] = String.format("%02x", bytes[i]);
            }
            return new StringBuilder().append("[").append(StringUtils.join(hex, ",")).append("]").toString();
        }
        
        return obj.toString() + " @ " + obj.getClass().getName();
    }
   
}
