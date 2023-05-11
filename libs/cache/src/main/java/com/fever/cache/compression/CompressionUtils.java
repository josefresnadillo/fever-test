package com.fever.cache.compression;

import io.vertx.reactivex.core.buffer.Buffer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
public final class CompressionUtils {
  private CompressionUtils() {
    // do nothing
  }

  public static String compressAndEncodeToBase64(final String data) throws IOException {
    return Base64.getEncoder().encodeToString(zip(data));
  }

  public static String decompressAndDecodeFromBase64(final Buffer data) throws IOException {
    return unzip(Base64.getDecoder().decode(data.getBytes()));
  }
  private static byte[] zip(final String data) throws IOException {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);

    gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
    gzipOutputStream.close();

    final byte[] zippedBytes = outputStream.toByteArray();
    outputStream.close();

    return zippedBytes;
  }

  private static String unzip(final byte[] zippedData) throws IOException {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zippedData);
    final GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
    final InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    final StringBuilder output = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      output.append(line);
    }

    bufferedReader.close();
    inputStreamReader.close();
    gzipInputStream.close();
    byteArrayInputStream.close();

    return output.toString();
  }
}
