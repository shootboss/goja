/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.i18n;

import com.google.common.base.Strings;
import goja.Goja;
import goja.Logger;
import goja.mvc.kit.Request;
import goja.init.InitConst;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-06-24 22:08
 * @since JDK 1.6
 */
public final class I18n {


    public static ThreadLocal<String> current = new ThreadLocal<String>();

    /**
     * Retrieve the current language or null
     *
     * @return The current language (fr, ja, it ...) or null
     */
    public static String get(HttpServletRequest request, HttpServletResponse response) {
        String locale = current.get();
        if (locale == null) {
            // we have a current request - lets try to resolve language from it
            resolvefrom(request, response);

            // get the picked locale
            locale = current.get();
        }

        return locale;
    }


    /**
     * Force the current language
     *
     * @param locale (fr, ja, it ...)
     * @return false if the language is not supported by the application
     */
    public static boolean set(String locale) {
        if (locale.equals("") || Goja.langs.contains(locale)) {
            current.set(locale);
            return true;
        } else {
            Logger.warn("Locale %s is not defined in your application.conf", locale);
            return false;
        }
    }

    /**
     * Clears the current language - This wil trigger resolving language from request
     * if not manually set.
     */
    public static void clear() {
        current.remove();
    }


    /**
     * Change language for next requests
     *
     * @param locale (e.g. "fr", "ja", "it", "en_ca", "fr_be", ...)
     */
    public static void change(String locale, HttpServletResponse response, HttpServletRequest request) {
        String closestLocale = findClosestMatch(Collections.singleton(locale));
        if (closestLocale == null) {
            // Give up
            return;
        }
        if (set(closestLocale)) {
            if (response != null) {
                // We have a current response in scope - set the language-cookie to store the selected language for the next requests
                Request.setCookie(request, response, Goja.configuration.getProperty("application.lang.cookie", "JAPP_LANG"), locale, 0, true);
            }
        }

    }

    /**
     * Given a set of desired locales, searches the set of locales supported by this Play! application and returns the closest match.
     *
     * @param desiredLocales a collection of desired locales. If the collection is ordered, earlier locales are preferred over later ones.
     *                       Locales should be of the form "[language]_[country" or "[language]", e.g. "en_CA" or "en".
     *                       The locale strings are case insensitive (e.g. "EN_CA" is considered the same as "en_ca").
     *                       Locales can also be of the form "[language]-[country", e.g. "en-CA" or "en".
     *                       They are still case insensitive, though (e.g. "EN-CA" is considered the same as "en-ca").
     * @return the closest matching locale. If no closest match for a language/country is found, null is returned
     */
    private static String findClosestMatch(Collection<String> desiredLocales) {
        ArrayList<String> cleanLocales = new ArrayList<String>(desiredLocales.size());
        //look for an exact match
        for (String a : desiredLocales) {
            a = a.replace("-", "_");
            cleanLocales.add(a);
            for (String locale : Goja.langs) {
                if (locale.equalsIgnoreCase(a)) {
                    return locale;
                }
            }
        }
        // Exact match not found, try language-only match.
        for (String a : cleanLocales) {
            int splitPos = a.indexOf("_");
            if (splitPos > 0) {
                a = a.substring(0, splitPos);
            }
            for (String locale : Goja.langs) {
                String langOnlyLocale;
                int localeSplitPos = locale.indexOf("_");
                if (localeSplitPos > 0) {
                    langOnlyLocale = locale.substring(0, localeSplitPos);
                } else {
                    langOnlyLocale = locale;
                }
                if (langOnlyLocale.equalsIgnoreCase(a)) {
                    return locale;
                }
            }
        }

        // We did not find a anything
        return null;
    }

    /**
     * Guess the language for current request in the following order:
     * <ol>
     * <li>if a <b>JAPP_LANG</b> cookie is set, use this value</li>
     * <li>if <b>Accept-Language</b> header is set, use it only if the JApp! application allows it.<br/>supported language may be defined in application configuration, eg : <em>play.langs=fr,en,de)</em></li>
     * <li>otherwise, server's locale language is assumed
     * </ol>
     *
     * @param request  the Request.
     * @param response the Response.
     */
    private static void resolvefrom(HttpServletRequest request, HttpServletResponse response) {
        // Check a cookie
        String cn = Goja.configuration.getProperty(InitConst.APP_I18N_COOKIE, Goja.appName + ".lang");
        final Cookie locale_cookie = Request.getCookie(request, cn);
        if (locale_cookie != null) {
            final String localeFromCookie = locale_cookie.getValue();
            if (!Strings.isNullOrEmpty(localeFromCookie)) {
                if (set(localeFromCookie)) {
                    // we're using locale from cookie
                    return;
                }
                Request.setCookie(request, response, cn, "", 0);
            }
        }

        String closestLocaleMatch = findClosestMatch(Request.acceptLanguage(request));
        if (closestLocaleMatch != null) {
            set(closestLocaleMatch);
        } else {
            // Did not find anything - use default
            setDefaultLocale();
        }

    }

    public static void setDefaultLocale() {
        if (Goja.langs.isEmpty()) {
            set("");
        } else {
            set(Goja.langs.get(0));
        }
    }

    /**
     * @param request  the Request.
     * @param response the Response.
     * @return the default locale if the Locale cannot be found otherwise the locale
     * associated to the current Lang.
     */
    public static Locale getLocale(HttpServletRequest request, HttpServletResponse response) {
        String localeStr = get(request, response);
        Locale locale = getLocale(localeStr);
        if (locale != null) {
            return locale;
        }
        return Locale.getDefault();
    }

    public static Locale getLocale(String localeStr) {
        Locale langMatch = null;
        for (Locale locale : Locale.getAvailableLocales()) {
            String lang = localeStr;
            int splitPos = lang.indexOf("_");
            if (splitPos > 0) {
                lang = lang.substring(0, splitPos);
            }
            if (locale.toString().equalsIgnoreCase(localeStr)) {
                return locale;
            } else if (locale.getLanguage().equalsIgnoreCase(lang)) {
                langMatch = locale;
            }
        }
        return langMatch;
    }
}
