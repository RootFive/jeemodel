package com.jeemodel.base.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

public final class ProjecetUtils {

	private ProjecetUtils() {

	}

	
	/**
	 * Fetches the {@link Name#IMPLEMENTATION_TITLE Implementation-Title}
	 * @param primarySource
	 * @return
	 */
	public static String title(Class<?> primarySource) {
		// 第1种：根据包信息获取
		String implementationVersion = primarySource.getPackage().getImplementationTitle();
		if (implementationVersion != null) {
			return implementationVersion;
		}

		// 第2种：根据Manifest地址获取
		Manifest manifest = getManifest(primarySource);
		if (manifest != null) {
			try {
				return getImplementationTitle(manifest);
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * Class that exposes the Spring Boot version
	 * Fetches the {@link Name#IMPLEMENTATION_VERSION Implementation-Version}
	 * @param primarySource
	 * @return
	 */
	public static String version(Class<?> primarySource) {
		// 第1种：根据包信息获取
		String implementationVersion = primarySource.getPackage().getImplementationVersion();
		if (implementationVersion != null) {
			return implementationVersion;
		}

		// 第2种：根据Manifest地址获取
		Manifest manifest = getManifest(primarySource);
		if (manifest != null) {
			try {
				return getImplementationVersion(manifest);
			} catch (IOException e) {
			}
		}
		return null;
	}

	public static Manifest getManifest(Class<?> primarySource) {
		CodeSource codeSource = primarySource.getProtectionDomain().getCodeSource();
		if (codeSource == null) {
			return null;
		}

		Manifest manifest = null;

		URI uri = null;
		URL codeSourceLocation = codeSource.getLocation();

		// 第1种：根据Jar地址获取
		try {
			URLConnection connection = codeSourceLocation.openConnection();
			if (connection instanceof JarURLConnection) {
				manifest = ((JarURLConnection) connection).getJarFile().getManifest();
				if (manifest != null) {
					return manifest;
				}
			}

			uri = codeSourceLocation.toURI();
			try (JarFile jarFile = new JarFile(new File(uri))) {
				manifest = jarFile.getManifest();
				if (manifest != null) {
					return manifest;
				}
			}
		} catch (Exception ex) {
		}

		// 第2种：根据项目target\classes\META-INF\MANIFEST.MF文件编译路径获取
		try {
			if (uri == null) {
				return null;
			}
			String MANIFEST_MF_FILE_PATH = uri.getPath() + "META-INF" + File.separatorChar + "MANIFEST.MF";
			manifest = new Manifest(new FileInputStream(MANIFEST_MF_FILE_PATH));
			return manifest;

		} catch (Exception ex) {

		}
		return null;
	}

	private static String getImplementationTitle(Manifest manifest) throws IOException {
		return manifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_TITLE);
	}

	private static String getImplementationVersion(Manifest manifest) throws IOException {
		return manifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
	}
}