package com.tencent.matrix.resource.processor;

import com.tencent.matrix.memorydump.MemoryDumpManager;
import com.tencent.matrix.resource.analyzer.model.DestroyedActivityInfo;
import com.tencent.matrix.resource.analyzer.model.HeapDump;
import com.tencent.matrix.resource.watcher.ActivityRefWatcher;
import com.tencent.matrix.util.MatrixLog;

import java.io.File;

/**
 * HPROF file dump processor using fork dump.
 *
 * @author aurorani
 * @since 2021/10/25
 */
public class ForkDumpProcessor extends BaseLeakProcessor {

    private static final String TAG = "Matrix.LeakProcessor.ForkDump";

    public ForkDumpProcessor(ActivityRefWatcher watcher) {
        super(watcher);
    }

    @Override
    public boolean process(DestroyedActivityInfo destroyedActivityInfo) {
        final long dumpStart = System.currentTimeMillis();

        final File hprof = getDumpStorageManager().newHprofFile();

        if (hprof == null) {
            MatrixLog.e(TAG, "cannot create hprof file, just ignore");
            return true;
        }

        if (!MemoryDumpManager.dumpBlock(hprof.getPath())) {
            MatrixLog.e(TAG, String.format("heap dump for further analyzing activity with key [%s] was failed, just ignore.",
                    destroyedActivityInfo.mKey));
            return true;
        }

        MatrixLog.i(TAG, String.format("dump cost=%sms refString=%s path=%s",
                System.currentTimeMillis() - dumpStart, destroyedActivityInfo.mKey, hprof.getPath()));

        getWatcher().markPublished(destroyedActivityInfo.mActivityName);
        getWatcher().triggerGc();

        getHeapDumpHandler().process(
                new HeapDump(hprof, destroyedActivityInfo.mKey, destroyedActivityInfo.mActivityName));

        return true;
    }
}
