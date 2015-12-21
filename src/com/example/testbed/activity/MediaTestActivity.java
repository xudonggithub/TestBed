package com.example.testbed.activity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.testbed.R;

public class MediaTestActivity extends Activity{

	private final String tag = this.getClass().getSimpleName();
    private AudioRecord recorder;
    private AudioTrack player;

    private MediaCodec encoder;
    private MediaCodec decoder;

    private short audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private short channelConfig = AudioFormat.CHANNEL_IN_MONO;

    private int bufferSize;
    private boolean isRecording;
    private boolean isPlaying;

    private Thread IOrecorder;
    private Thread IOmediaRecorder;
    
    private Thread IOudpPlayer;


    private DatagramSocket ds;
    private final int localPort = 39000;
private String filePath = Environment.getExternalStorageDirectory().getPath().concat(File.separator).concat("andioTest.m4p");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IOmediaRecorder = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					mMediaRecorder.prepare();
					mMediaRecorder.start();
					byte[] buffer = new byte[65535];
					InputStream fis = audioReceiverLocalSocket.getInputStream();
					DataInputStream dis = new DataInputStream(fis);
					DatagramPacket packet;
					byte[] outData;
					while (true) {
						int aaclength = dis.available();
						int dataLen = aaclength;
						if (aaclength <= 0) 
							continue;
					
						if (aaclength > buffer.length) {
							while (dis.available() > 0) {
								if (dis.available() > buffer.length) {
									dataLen = dis.read(buffer, 0, buffer.length);
								} else {
									dataLen = dis.read(buffer, 0, dis.available());
								}
							}
						}
						else
							dis.read(buffer, 0, dataLen);
						
						Log.d(tag, "Send outData.length:"+dataLen + " bytes encoded");
						outData = Arrays.copyOfRange(buffer, 0, dataLen);
						outputMediaRes(outData);
//						packet = new DatagramPacket(outData, outData.length,
//                                    InetAddress.getByName("127.0.0.1"), localPort);
//                        ds.send(packet);
                        
					}	
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
        
        IOrecorder = new Thread(new Runnable()
        {
        	private void addADTStoPacket(byte[] packet, int packetLen) {
        	    int profile = 2;  //AAC LC
        	                      //39=MediaCodecInfo.CodecProfileLevel.AACObjectELD;
        	    int freqIdx = 4;  //44.1KHz
        	    int chanCfg = 1;  //CPE

        	    // fill in ADTS data
        	    packet[0] = (byte)0xFF;
        	    packet[1] = (byte)0xF1;
        	    packet[2] = (byte)(((profile-1)<<6) + (freqIdx<<2) +(chanCfg>>2));
        	    packet[3] = (byte)(((chanCfg&0x3)<<6) + (packetLen>>11));
        	    packet[4] = (byte)((packetLen&0x7FF) >> 3);
        	    packet[5] = (byte)(((packetLen&7)<<5) + 0x1F);
        	    packet[6] = (byte)0xFC;
        	
        	}
            public void run()
            {
                int read;
                byte[] buffer1 = new byte[bufferSize];

                ByteBuffer[] inputBuffers;
                ByteBuffer[] outputBuffers;

                ByteBuffer inputBuffer;
                ByteBuffer outputBuffer;

                MediaCodec.BufferInfo bufferInfo;
                int inputBufferIndex;
                int outputBufferIndex;

                byte[] outData;

                DatagramPacket packet;
                try
                {
                    encoder.start();
                    recorder.startRecording();
                    isRecording = true;
                    File file = new File(filePath);
                    FileOutputStream fos = new FileOutputStream(file);;
                    while (isRecording)
                    {
                        read = recorder.read(buffer1, 0, bufferSize);
                       // Log.d("AudioRecoder", read + " bytes read");
                        //------------------------

                        inputBuffers = encoder.getInputBuffers();
                        outputBuffers = encoder.getOutputBuffers();
                        inputBufferIndex = encoder.dequeueInputBuffer(-1);
                        if (inputBufferIndex >= 0)
                        {
                            inputBuffer = inputBuffers[inputBufferIndex];
                            inputBuffer.clear();

                            inputBuffer.put(buffer1);

                            encoder.queueInputBuffer(inputBufferIndex, 0, buffer1.length, 0, 0);
                        }

                        bufferInfo = new MediaCodec.BufferInfo();
                        outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 0);



                        while (outputBufferIndex >= 0)
                        {
                            outputBuffer = outputBuffers[outputBufferIndex];

                            outputBuffer.position(bufferInfo.offset);
                            outputBuffer.limit(bufferInfo.offset + bufferInfo.size);

                            outData = new byte[bufferInfo.size];

                            if(true) {
                            	int outPacketSize = bufferInfo.size + 7; 
                            	byte[] data = new byte[outPacketSize];  //space for ADTS header included
                                addADTStoPacket(data, outPacketSize);
                                outputBuffer.get(data, 7, bufferInfo.size);
                                outputBuffer.position(bufferInfo.offset);
                                fos.write(data, 0, outPacketSize);
                            }
                            outputBuffer.get(outData);
                            //-------------
                            packet = new DatagramPacket(outData, outData.length,
                                    InetAddress.getByName("127.0.0.1"), localPort);
                            ds.send(packet);
                            //------------
                          
                            encoder.releaseOutputBuffer(outputBufferIndex, false);
                            outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 0);

                        }
                        if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) 
                        {
                             outputBuffers = encoder.getOutputBuffers();
                        } 
                        else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) 
                        {
                        }
                        // ----------------------;

                    }
                    fos.close();
                    encoder.stop();
                    recorder.stop();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



        IOudpPlayer = new Thread(new Runnable()
        {
            public void run()
            {
                SocketAddress sockAddress;
                String address;

                int len = 1024 * 10;
                byte[] buffer2 = new byte[len];
                DatagramPacket packet;

                byte[] data;

                ByteBuffer[] inputBuffers;
                ByteBuffer[] outputBuffers;

                ByteBuffer inputBuffer;
                ByteBuffer outputBuffer;

                MediaCodec.BufferInfo bufferInfo;
                int inputBufferIndex;
                int outputBufferIndex;
                byte[] outData;
                try
                {
                    player.play();
                    decoder.start();
                    isPlaying = true;
                    while (isPlaying)
                    {
                        try
                        {
                            packet = new DatagramPacket(buffer2, len);
                            ds.receive(packet);
                            Log.d(tag, "cxd receive packet.length:"+len + " bytes encoded");
                            sockAddress = packet.getSocketAddress();
                            address = sockAddress.toString();

                         //   Log.d("UDP Receiver"," received !!! from " + address);

                            data = new byte[packet.getLength()];
                            System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());

                           // Log.d("UDP Receiver",  data.length + " bytes received");

                            //===========
                            inputBuffers = decoder.getInputBuffers();
                            outputBuffers = decoder.getOutputBuffers();
                            inputBufferIndex = decoder.dequeueInputBuffer(-1);
                            if (inputBufferIndex >= 0)
                            {
                                inputBuffer = inputBuffers[inputBufferIndex];
                                inputBuffer.clear();

                                inputBuffer.put(data);

                                decoder.queueInputBuffer(inputBufferIndex, 0, data.length, 0, 0);
                            }

                            bufferInfo = new MediaCodec.BufferInfo();
                            outputBufferIndex = decoder.dequeueOutputBuffer(bufferInfo, 0);

                            while (outputBufferIndex >= 0)
                            {
                                outputBuffer = outputBuffers[outputBufferIndex];

                                outputBuffer.position(bufferInfo.offset);
                                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);

                                outData = new byte[bufferInfo.size];
                                outputBuffer.get(outData);

                              //  Log.d("AudioDecoder", outData.length + " bytes decoded");

                                player.write(outData, 0, outData.length);

                                decoder.releaseOutputBuffer(outputBufferIndex, false);
                                outputBufferIndex = decoder.dequeueOutputBuffer(bufferInfo, 0);

                            }

                            //===========

                        }
                        catch (IOException e)
                        {
                        	e.printStackTrace();
                        }
                    }

                    decoder.stop();
                    player.stop();

                }
                catch (Exception e)
                {
                }
            }
        });

        
//===========================================================
        int rate = findAudioRecord();
//        int rate = 44100;
//        initMediaRecord();
        if (rate != -1)
        {
            Log.v(tag, "=========media  ready: " + rate);
            Log.v(tag, "=========media channel ,ready: " + channelConfig);

            boolean encoderReady = setEncoder(rate);
            Log.v(tag, "=========encoder , ready: " + encoderReady);
            if (encoderReady)
            {
                boolean decoderReady = setDecoder(rate);
                Log.v(tag, "=========decoder ,ready: " + decoderReady);
                if (decoderReady)
                {
                    Log.d(tag, "=======bufferSize========, " + bufferSize);
                    try
                    {
                        setPlayer(rate);

                        ds = new DatagramSocket(localPort);
//                        IOudpPlayer.start();

                        IOrecorder.start();
//                        IOmediaRecorder.start();

                    }
                    catch (SocketException e)
                    {
                        e.printStackTrace();
                    }


                }

            }
        }
    }

    String outputMediaResFile = Environment.getExternalStorageDirectory().getAbsolutePath()
			.concat(File.separator).concat("chatdump_m").concat(File.separator).concat("mediaOut");
	int outputindex = 1;
	private void outputMediaRes(byte[] input) {
		FileOutputStream fos;
		try{
			///if(input[4] == 0x65 || input[4] == 0x67 || input[4] == 0x68) {
			fos = new FileOutputStream( new File(outputMediaResFile.concat(outputindex+".txt")), true);
			fos.write(input);
			fos.flush();
			fos.close();
		//	}
			outputindex ++;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	String outputRecordFile = Environment.getExternalStorageDirectory().getAbsolutePath()
			.concat(File.separator).concat("chatdump_r").concat(File.separator).concat("recordout");
	int outputRecordFileindex = 1;
	private void outpuRecordRes(byte[] input) {
		FileOutputStream fos;
		try{
			///if(input[4] == 0x65 || input[4] == 0x67 || input[4] == 0x68) {
			fos = new FileOutputStream( new File(outputRecordFile.concat(outputRecordFileindex+".txt")), true);
			fos.write(input);
			fos.flush();
			fos.close();
		//	}
			outputRecordFileindex ++;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
    protected void onPause()
    {
    	releaseLocalSocket();
        recorder.release();
        player.release();
        encoder.release();
        decoder.release();
        super.onPause();
    }
/*
    protected void onResume()
    {

        // isRecording = true;
    }

    protected void onPause()
    {

        isRecording = false;
    }
*/
    private MediaRecorder mMediaRecorder;
    private LocalSocket audioSenderLocalSocket;
    private LocalSocket audioReceiverLocalSocket;
    private LocalServerSocket audioLocalServerSocket;
    private void initLocalSocket() {
		try {
			audioLocalServerSocket = new LocalServerSocket("AudioCamera");
			
			audioReceiverLocalSocket = new LocalSocket();
			audioReceiverLocalSocket.connect(new LocalSocketAddress("AudioCamera"));
			audioReceiverLocalSocket.setReceiveBufferSize(20480);
			audioReceiverLocalSocket.setSendBufferSize(20480);//

			audioSenderLocalSocket = audioLocalServerSocket.accept();
			audioSenderLocalSocket.setReceiveBufferSize(20480);
			audioSenderLocalSocket.setSendBufferSize(20480);
			
		} catch (IOException e) {
			e.printStackTrace();
			releaseLocalSocket();
		}
	}
    private void releaseLocalSocket() {
    	try {
	    	if(audioSenderLocalSocket != null) {
	    		audioSenderLocalSocket.close();
	    		audioSenderLocalSocket = null;
	    	}
	    	if(audioReceiverLocalSocket != null) {
	    		audioReceiverLocalSocket.close();
	    		audioReceiverLocalSocket = null;
	    	}
	    	if(audioLocalServerSocket != null) {
	    		audioLocalServerSocket.close();
	    		audioLocalServerSocket = null;
	    	}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    private void initMediaRecord() {
    	initLocalSocket();
    	mMediaRecorder = new MediaRecorder();


		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		mMediaRecorder.setAudioChannels(1);
		mMediaRecorder.setAudioEncodingBitRate(128000);
		mMediaRecorder.setAudioSamplingRate(44100);//8000  44100

		mMediaRecorder.setOutputFile(audioSenderLocalSocket.getFileDescriptor());
//		mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getPath().concat(File.separator).concat("test2.m4p"));
    }

    private int findAudioRecord()
    {
        for (int rate : new int[]{44100})
        {
            try
            {
                Log.v(tag, "===========Attempting rate= "+ rate + "Hz, bits: " + audioFormat + ", channel: " + channelConfig);
                bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                if (bufferSize != AudioRecord.ERROR_BAD_VALUE)
                {
                    // check if we can instantiate and have a success
                    recorder = new AudioRecord(AudioSource.MIC, rate, channelConfig, audioFormat, bufferSize);

                    if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                    {
                        Log.v(tag, "===========final rate :"+ rate + "Hz, bits: " + audioFormat + ", channel: " + channelConfig);

                        return rate;
                    }
                }
            }
            catch (Exception e)
            {
                Log.v("error", "" + rate);
            }

        }
        return -1;
    }

    private boolean setEncoder(int rate)
    {
        try {
			encoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MediaFormat format = new MediaFormat();
        format.setString(MediaFormat.KEY_MIME, "audio/mp4a-latm");
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, rate);//rate = 44100
        format.setInteger(MediaFormat.KEY_BIT_RATE, 64 * 1024);//AAC-HE 64kbps
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
        encoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        return true;
    }

    private boolean setDecoder(int rate)
    {
        try {
			decoder = MediaCodec.createDecoderByType("audio/mp4a-latm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MediaFormat format = new MediaFormat();
        format.setString(MediaFormat.KEY_MIME, "audio/mp4a-latm");
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, rate);
        format.setInteger(MediaFormat.KEY_BIT_RATE, 64 * 1024);//AAC-HE 64kbps
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);

        decoder.configure(format, null, null, 0);

        return true;
    }

    private boolean setPlayer(int rate)
    {
        int bufferSizePlayer = AudioTrack.getMinBufferSize(rate, AudioFormat.CHANNEL_OUT_MONO, audioFormat);
        Log.d("====buffer Size player ", String.valueOf(bufferSizePlayer));

        player= new AudioTrack(AudioManager.STREAM_MUSIC, rate, AudioFormat.CHANNEL_OUT_MONO, audioFormat, bufferSizePlayer, AudioTrack.MODE_STREAM);


        if (player.getState() == AudioTrack.STATE_INITIALIZED)
        {

            return true;
        }
        else
        {
            return false;
        }

    }



}
