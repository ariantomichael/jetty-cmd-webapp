package com.example;

import java.nio.ByteBuffer;

import com.zaxxer.nuprocess.NuAbstractProcessHandler;
import com.zaxxer.nuprocess.NuProcess;

class ProcessHandler extends NuAbstractProcessHandler {
    private NuProcess nuProcess;
    public String output;
    ProcessHandler(){
        this.output = "";
    }
    @Override
    public void onStart(NuProcess nuProcess) {
       this.nuProcess = nuProcess;
    }
    
    @Override
    public boolean onStdinReady(ByteBuffer buffer) {
       buffer.put("Hello world!".getBytes());
       buffer.flip();
       return false; // false means we have nothing else to write at this time
    }
 
    @Override
    public void onStdout(ByteBuffer buffer, boolean closed) {
       if (!closed) {
          byte[] bytes = new byte[buffer.remaining()];
          // You must update buffer.position() before returning (either implicitly,
          // like this, or explicitly) to indicate how many bytes your handler has consumed.
          buffer.get(bytes);
            this.output = new String(bytes);
          // For this example, we're done, so closing STDIN will cause the "cat" process to exit
          nuProcess.closeStdin(true);
       }
    }

 }