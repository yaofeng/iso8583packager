package com.inspur.demo.message;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

@BaseName("logs")
@LocaleData({ @Locale("zh_CN") })
public enum LogResources {
    PACK_BEGIN,
    UNPACK_BEGIN
}
