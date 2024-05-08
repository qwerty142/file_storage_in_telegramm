package ru.tgfs;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import jnr.ffi.Pointer;
import jnr.ffi.types.off_t;
import jnr.ffi.types.size_t;
import ru.serce.jnrfuse.ErrorCodes;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.FuseStubFS;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseFileInfo;

public class TelegramFS extends FuseStubFS {
    public static final String HELLO_PATH = "/hello";
    public static final String HELLO_STR = "Hello World!";

    @Override
    public int getattr(String path, FileStat stat) {
        int res = 0;
        if (Objects.equals(path, "/")) {
            stat.st_mode.set(FileStat.S_IFDIR | FileStat.ALL_READ | FileStat.S_IXUGO | FileStat.S_IRUSR);
            stat.st_nlink.set(2); // One link for '.' and another for '..'
        } else if (HELLO_PATH.equals(path)) {
            /*
              0 for OCTAL
              R+W+X == 4+2+1
              Three digits are for OWNER-GROUP-OTHERS
              So here we have only READ access for owner, groups and others
             */
            stat.st_mode.set(FileStat.S_IFREG | FileStat.ALL_READ);
            stat.st_nlink.set(1);
            stat.st_size.set(HELLO_STR.getBytes().length);
        } else {
            res = -ErrorCodes.ENOENT();
        }
        return res;
    }

    @Override
    public int readdir(String path, Pointer buf, FuseFillDir filter, @off_t long offset, FuseFileInfo fi) {
        if (!"/".equals(path)) {
            return -ErrorCodes.ENOENT();
        }

        filter.apply(buf, ".", null, 0);
        filter.apply(buf, "..", null, 0);
        filter.apply(buf, HELLO_PATH.substring(1), null, 0);
        return 0;
    }

    @Override
    public int open(String path, FuseFileInfo fi) {
        if (!HELLO_PATH.equals(path)) {
            return -ErrorCodes.ENOENT();
        }
        return 0;
    }

    @Override
    public int read(String path, Pointer buf, @size_t long size, @off_t long offset, FuseFileInfo fi) {
        if (!HELLO_PATH.equals(path)) {
            return -ErrorCodes.ENOENT();
        }

        byte[] bytes = HELLO_STR.getBytes();
        int length = bytes.length;
        if (offset < length) {
            if (offset + size > length) {
                size = length - offset;
            }
            buf.put(0, bytes, 0, bytes.length);
        } else {
            size = 0;
        }
        return (int) size;
    }

    public static void main(String[] args) {
        TelegramFS stub = new TelegramFS();
        try {
            if (args.length != 1) {
                System.err.println("Usage: TelegramFS <MountPoint>");
                System.exit(1);
            }
            stub.mount(Paths.get(args[0]), true, true);
        } finally {
            stub.umount();
        }
    }
}