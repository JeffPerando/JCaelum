
package com.elusivehawk.caelum.render.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * OpenGL v3.x wrapper interface
 * 
 * @author Elusivehawk
 */
public interface IGL3Impl
{
	void glBeginConditionalRender(int id, int mode);
	
	void glBeginTransformFeedback(int primitiveMode);
	
	void glBindBufferBase(int target, int index, int buffer);
	
	void glBindBufferRange(int target, int index, int buffer, long offset, long size);
	
	void glBindFragDataLocation(int program, int colorNumber, String name);
	
	void glBindFragDataLocationIndexed(int program, int colorNumber, int index, String name);
	
	void glBindFramebuffer(int target, int framebuffer);
	
	void glBindRenderbuffer(int target, int renderbuffer);
	
	void glBindSampler(int unit, int sampler);
	
	void glBindVertexArray(int array);
	
	void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter);
	
	int glCheckFramebufferStatus(int target);
	
	void glClampColor(int target, int clamp);
	
	void glClearBuffer(int buffer, int drawbuffer, FloatBuffer value);
	
	void glClearBuffer(int buffer, int drawbuffer, IntBuffer value);
	
	void glClearBufferf(int buffer, int drawbuffer, ByteBuffer value);
	
	void glClearBufferi(int buffer, int drawbuffer, ByteBuffer value);
	
	void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil);
	
	int glClientWaitSync(long sync, int flags, long timeout);
	
	void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a);
	
	void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size);
	
	void glDeleteFramebuffer(int framebuffer);
	
	void glDeleteFramebuffers(int[] framebuffers);
	
	void glDeleteRenderbuffer(int renderbuffer);
	
	void glDeleteRenderbuffers(int[] renderbuffer);
	
	void glDeleteSampler(int sampler);
	
	void glDeleteSamplers(int[] samplers);
	
	void glDeleteSync(long sync);
	
	void glDeleteVertexArray(int array);
	
	void glDeleteVertexArrays(int[] arrays);
	
	void glDisablei(int target, int index);
	
	void glDrawArraysInstanced(int mode, int first, int argcount, int primcount);
	
	void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex);
	
	void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset);
	
	void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount);
	
	void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex);
	
	void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex);
	
	void glEnablei(int target, int index);
	
	void glEndConditionalRender();
	
	void glEndTransformFeedback();
	
	long glFenceSync(int condition, int flags);
	
	void glFlushMappedBufferRange(int target, long offset, long length);
	
	void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer);
	
	void glFramebufferTexture(int target, int attachment, int texture, int level);
	
	void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level);
	
	void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);
	
	void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset);
	
	void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer);
	
	void glGenerateMipmap(int target);
	
	int glGenFramebuffer();
	
	int[] glGenFramebuffers(int argcount);
	
	int glGenRenderbuffer();
	
	int[] glGenRenderbuffers(int argcount);
	
	int glGenSampler();
	
	int[] glGenSamplers(int argcount);
	
	int glGenVertexArray();
	
	int[] glGenVertexArrays(int argcount);
	
	int glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname);
	
	String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize);
	
	String glGetActiveUniformName(int program, int uniformIndex, int bufSize);
	
	int[] glGetActiveUniforms(int program, int[] uniformIndices, int pname);
	
	int glGetActiveUniformsi(int program, int uniformIndex, int pname);
	
	boolean glGetBoolean(int value, int index);
	
	boolean[] glGetBoolean(int value, int index, int argcount);
	
	long[] glGetBufferParameter(int target, int pname, int argcount);
	
	long glGetBufferParameteri64(int target, int pname);
	
	int glGetFragDataIndex(int program, String name);
	
	int glGetFragDataLocation(int program, String name);
	
	int[] glGetFramebufferAttachmentParameter(int target, int attachment, int pname, int argcount);
	
	int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname);
	
	int glGetIntegeri(int value, int index);
	
	int[] glGetInteger(int value, int index, int argcount);
	
	long glGetInteger64i(int pname);
	
	long glGetInteger64i(int value, int index);
	
	long[] glGetInteger64(int value, int index, int argcount);
	
	long[] glGetInteger64(int pname, int argcount);
	
	float[] glGetMultisample(int pname, int index, int argcount);
	
	long[] glGetQueryObject(int id, int pname, int argcount);
	
	long glGetQueryObjecti64(int id, int pname);
	
	long[] glGetQueryObjectu(int id, int pname, int argcount);
	
	long glGetQueryObjectui64(int id, int pname);
	
	int[] glGetRenderbufferParameter(int target, int pname, int argcount);
	
	int glGetRenderbufferParameteri(int target, int pname);
	
	float[] glGetSamplerParameter(int sampler, int pname, int argcount);
	
	int[] glGetSamplerParameteri(int sampler, int pname, int argcount);
	
	float glGetSamplerParameterf(int sampler, int pname);
	
	int glGetSamplerParameteri(int sampler, int pname);
	
	int[] glGetSamplerParameterI(int sampler, int pname, int argcount);
	
	int glGetSamplerParameterIi(int sampler, int pname);
	
	int[] glGetSamplerParameterIu(int sampler, int pname, int argcount);
	
	int glGetSamplerParameterIui(int sampler, int pname);
	
	String glGetStringi(int name, int index);
	
	int[] glGetSync(long sync, int pname, int[] length, int argcount);
	
	int glGetSynci(long sync, int pname);
	
	int[] glGetTexParameterI(int target, int pname, int argcount);
	
	int glGetTexParameterIi(int target, int pname);
	
	int[] glGetTexParameterIu(int target, int pname, int argcount);
	
	int glGetTexParameterIui(int target, int pname);
	
	String glGetTransformFeedbackVarying(int program, int index, int bufSize, int[] size, int[] type);
	
	int glGetUniformBlockIndex(int program, String uniformBlockName);
	
	int[] glGetUniformIndices(int program, String[] uniformNames);
	
	int[] glGetUniformu(int program, int location, int argcount);
	
	int[] glGetVertexAttribI(int index, int pname, int argcount);
	
	int[] glGetVertexAttribIu(int index, int pname, int argcount);
	
	boolean glIsEnabledi(int target, int index);
	
	boolean glIsFramebuffer(int framebuffer);
	
	boolean glIsRenderbuffer(int renderbuffer);
	
	boolean glIsSampler(int sampler);
	
	boolean glIsSync(long sync);
	
	boolean glIsVertexArray(int array);
	
	ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer);
	
	void glPrimitiveRestartIndex(int index);
	
	void glProvokingVertex(int mode);
	
	void glQueryCounter(int id, int target);
	
	void glRenderbufferStorage(int target, int internalformat, int width, int height);
	
	void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height);
	
	void glSampleMaski(int index, int mask);
	
	float[] glSamplerParameterf(int sampler, int pname, int argcount);
	
	int[] glSamplerParameter(int sampler, int pname, int argcount);
	
	void glSamplerParameterf(int sampler, int pname, float param);//TODO Check usage
	
	void glSamplerParameteri(int sampler, int pname, int param);;
	
	int[] glSamplerParameterI(int sampler, int pname, int argcount);
	
	int[] glSamplerParameterIu(int sampler, int pname, int argcount);
	
	void glTexBuffer(int target, int internalformat, int buffer);
	
	void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations);
	
	void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations);
	
	int[] glTexParameterI(int target, int pname, int argcount);
	
	void glTexParameterIi(int target, int pname, int param);
	
	int[] glTexParameterIu(int target, int pname, int argcount);
	
	void glTexParameterIui(int target, int pname, int param);
	
	void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode);
	
	void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding);
	
	void glVertexAttribDivisor(int index, int divisor);
	
	void glVertexAttribI1(int index, int[] v);
	
	void glVertexAttribI1i(int index, int x);
	
	void glVertexAttribI1u(int index, int[] v);
	
	void glVertexAttribI1ui(int index, int x);
	
	void glVertexAttribI2(int index, int[] v);
	
	void glVertexAttribI2i(int index, int x, int y);
	
	void glVertexAttribI2u(int index, int[] v);
	
	void glVertexAttribI2ui(int index, int x, int y);
	
	void glVertexAttribI3(int index, int[] v);
	
	void glVertexAttribI3i(int index, int x, int y, int z);
	
	void glVertexAttribI3u(int index, int[] v);
	
	void glVertexAttribI3ui(int index, int x, int y, int z);
	
	void glVertexAttribI4(int index, int[] v);
	
	void glVertexAttribI4i(int index, int x, int y, int z, int w);
	
	void glVertexAttribI4u(int index, int[] v);
	
	void glVertexAttribI4ui(int index, int x, int y, int z, int w);
	
	void glVertexAttribIPointer(int index, int size, int type, int stride, int[] buffer);
	
	void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset);
	
	void glVertexAttribP1u(int index, int type, boolean normalized, int[] value);
	
	void glVertexAttribP1ui(int index, int type, boolean normalized, int value);
	
	void glVertexAttribP2u(int index, int type, boolean normalized, int[] value);
	
	void glVertexAttribP2ui(int index, int type, boolean normalized, int value);
	
	void glVertexAttribP3u(int index, int type, boolean normalized, int[] value);
	
	void glVertexAttribP3ui(int index, int type, boolean normalized, int value);
	
	void glVertexAttribP4u(int index, int type, boolean normalized, int[] value);
	
	void glVertexAttribP4ui(int index, int type, boolean normalized, int value);
	
	void glVertexP2u(int type, int[] value);
	
	void glVertexP2ui(int type, int value);
	
	void glVertexP3u(int type, int[] value);
	
	void glVertexP3ui(int type, int value);
	
	void glVertexP4u(int type, int[] value);
	
	void glVertexP4ui(int type, int value);
	
	void glWaitSync(long sync, int flags, long timeout);
	
}
