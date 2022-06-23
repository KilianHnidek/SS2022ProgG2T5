package at.ac.fhcampuswien.downloader;

import at.ac.fhcampuswien.NewsApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Class is needed for exercise 4 - ignore for exercise 3 solution
public class ParallelDownloader extends at.ac.fhcampuswien.downloader.Downloader {

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) throws NewsApiException {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime
                .getRuntime().availableProcessors());

        List<Future<String>> fileDownloaders = new ArrayList<>();

        try {
            for (int i = 0; i < urls.size(); i++) {
                int j = i;
                Callable<String> task = () -> saveUrl2File(urls.get(j));
                fileDownloaders.add(pool.submit(task));
            }

            int resCnt = 0;
            for (Future<String> downloader : fileDownloaders) {
                if (downloader.get() != null) {
                    resCnt++;
                }
            }

            return resCnt;
        } catch (Exception e) {
            throw new NewsApiException("Different problem occurred in " + this.getClass().getName() + ". Message: " + e.getMessage());
        }
    }
}