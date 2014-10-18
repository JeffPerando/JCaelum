
package com.elusivehawk.engine.render.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 
 * OpenGL 3.x wrapper interface.
 * 
 * @author Elusivehawk
 */
public interface IGL3
{
	void glBeginConditionalRender(int id, int mode) throws GLException;
	
	void glBeginTransformFeedback(int primitiveMode) throws GLException;
	
	void glBindBufferBase(int target, int index, int buffer) throws GLException;
	
	void glBindBufferRange(int target, int index, int buffer, long offset, long size) throws GLException;
	
	void glBindFragDataLocation(int program, int colorNumber, String name) throws GLException;
	
	void glBindFragDataLocationIndexed(int program, int colorNumber, int index, String name) throws GLException;
	
	default void glBindFramebuffer(GLEnumFBType target, int framebuffer)
	{
		this.glBindFramebuffer(target.gl, framebuffer);
		
	}
	
	void glBindFramebuffer(int target, int framebuffer) throws GLException;
	
	default void glBindRenderbuffer(int renderbuffer) throws GLException
	{
		this.glBindRenderbuffer(GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	void glBindRenderbuffer(int target, int renderbuffer) throws GLException;
	
	void glBindSampler(int unit, int sampler) throws GLException;
	
	void glBindSamplers(int first, int argcount, IntBuffer samplers) throws GLException;
	
	void glBindVertexArray(int array) throws GLException;
	
	void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) throws GLException;
	
	default int glCheckFramebufferStatus(GLEnumFBType target) throws GLException
	{
		return this.glCheckFramebufferStatus(target.gl);
	}
	
	int glCheckFramebufferStatus(int target) throws GLException;
	
	void glClampColor(int target, int clamp) throws GLException;
	
	void glClearBuffer(int buffer, int drawbuffer, int type, Buffer value) throws GLException;
	
	void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) throws GLException;
	
	void glClearBufferu(int buffer, int drawbuffer, IntBuffer value) throws GLException;
	
	int glClientWaitSync(long sync, int flags, long timeout) throws GLException;
	
	void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a) throws GLException;
	
	void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size) throws GLException;
	
	void glDeleteFramebuffer(int framebuffer) throws GLException;
	
	void glDeleteFramebuffers(int[] framebuffers) throws GLException;
	
	void glDeleteRenderbuffer(int renderbuffer) throws GLException;
	
	void glDeleteRenderbuffers(int[] renderbuffer) throws GLException;
	
	void glDeleteSampler(int sampler) throws GLException;
	
	void glDeleteSamplers(int[] samplers) throws GLException;
	
	void glDeleteSync(long sync) throws GLException;
	
	void glDeleteVertexArray(int array) throws GLException;
	
	void glDeleteVertexArrays(int[] arrays) throws GLException;
	
	void glDisablei(int target, int index) throws GLException;
	
	void glDrawArraysInstanced(int mode, int first, int argcount, int primcount) throws GLException;
	
	void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException;
	
	void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset) throws GLException;
	
	void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) throws GLException;
	
	void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex) throws GLException;
	
	void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException;
	
	void glEnablei(int target, int index) throws GLException;
	
	void glEndConditionalRender() throws GLException;
	
	void glEndTransformFeedback() throws GLException;
	
	long glFenceSync(int condition, int flags) throws GLException;
	
	void glFlushMappedBufferRange(int target, long offset, long length) throws GLException;
	
	default void glFramebufferRenderbuffer(GLEnumFBType target, GLEnumFBAttach attachment, int renderbuffer) throws GLException
	{
		this.glFramebufferRenderbuffer(target, attachment, 0, renderbuffer);
		
	}
	
	default void glFramebufferRenderbuffer(GLEnumFBType target, GLEnumFBAttach attachment, int count, int renderbuffer) throws GLException
	{
		this.glFramebufferRenderbuffer(target.gl, attachment.gl + count, GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) throws GLException;
	
	default void glFramebufferTexture(GLEnumFBType target, GLEnumFBAttach attachment, int texture, int level) throws GLException
	{
		this.glFramebufferTexture(target, attachment, 0, texture, level);
		
	}
	
	default void glFramebufferTexture(GLEnumFBType target, GLEnumFBAttach attachment, int count, int texture, int level) throws GLException
	{
		this.glFramebufferTexture(target.gl, attachment.gl + count, texture, level);
		
	}
	
	void glFramebufferTexture(int target, int attachment, int texture, int level) throws GLException;
	
	default void glFramebufferTexture1D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level) throws GLException
	{
		this.glFramebufferTexture1D(target, attachment, 0, textarget, texture, level);
		
	}
	
	default void glFramebufferTexture1D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level) throws GLException
	{
		this.glFramebufferTexture1D(target.gl, attachment.gl + count, textarget, texture, level);
		
	}
	
	void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level) throws GLException;
	
	default void glFramebufferTexture2D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level) throws GLException
	{
		this.glFramebufferTexture2D(target, attachment, 0, textarget, texture, level);
		
	}
	
	default void glFramebufferTexture2D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level) throws GLException
	{
		this.glFramebufferTexture2D(target.gl, attachment.gl + count, textarget, texture, level);
		
	}
	
	void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) throws GLException;
	
	default void glFramebufferTexture3D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level, int zoffset) throws GLException
	{
		this.glFramebufferTexture3D(target, attachment, 0, textarget, texture, level, zoffset);
		
	}
	
	default void glFramebufferTexture3D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level, int zoffset) throws GLException
	{
		this.glFramebufferTexture3D(target.gl, attachment.gl + count, textarget, texture, level, zoffset);
		
	}
	
	void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset) throws GLException;
	
	void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) throws GLException;
	
	default void glGenerateMipmap(GLEnumTexture target) throws GLException
	{
		this.glGenerateMipmap(target.gl);
		
	}
	
	void glGenerateMipmap(int target) throws GLException;
	
	int glGenFramebuffer() throws GLException;
	
	int[] glGenFramebuffers(int argcount) throws GLException;
	
	int glGenRenderbuffer() throws GLException;
	
	int[] glGenRenderbuffers(int argcount) throws GLException;
	
	int glGenSampler() throws GLException;
	
	int[] glGenSamplers(int argcount) throws GLException;
	
	int glGenVertexArray() throws GLException;
	
	int[] glGenVertexArrays(int argcount) throws GLException;
	
	int[] glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname) throws GLException;
	
	String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize) throws GLException;
	
	String glGetActiveUniformName(int program, int uniformIndex, int bufSize) throws GLException;
	
	int[] glGetActiveUniforms(int program, int[] uniformIndices, int pname) throws GLException;
	
	int glGetActiveUniformsi(int program, int uniformIndex, int pname) throws GLException;
	
	boolean glGetBoolean(int value, int index) throws GLException;
	
	boolean[] glGetBoolean(int value, int index, int argcount) throws GLException;
	
	long[] glGetBufferParameter(int target, int pname, int argcount) throws GLException;
	
	long glGetBufferParameteri64(int target, int pname) throws GLException;
	
	int glGetFragDataIndex(int program, String name) throws GLException;
	
	int glGetFragDataLocation(int program, String name) throws GLException;
	
	int[] glGetFramebufferAttachmentParameter(int target, int attachment, int pname, int argcount);
	
	int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname) throws GLException;
	
	int glGetIntegeri(int value, int index) throws GLException;
	
	int[] glGetInteger(int value, int index, int argcount) throws GLException;
	
	long glGetInteger64i(int pname) throws GLException;
	
	long glGetInteger64i(int value, int index) throws GLException;
	
	long[] glGetInteger64(int value, int index, int argcount) throws GLException;
	
	long[] glGetInteger64(int pname, int argcount) throws GLException;
	
	float[] glGetMultisample(int pname, int index, int argcount) throws GLException;
	
	long[] glGetQueryObject(int id, int pname, int argcount) throws GLException;
	
	long glGetQueryObjecti64(int id, int pname) throws GLException;
	
	int[] glGetQueryObjectu(int id, int pname, int argcount) throws GLException;
	
	long[] glGetQueryObjectu64(int id, int pname, int argcount) throws GLException;
	
	long glGetQueryObjectui64(int id, int pname) throws GLException;
	
	int[] glGetRenderbufferParameter(int target, int pname, int argcount) throws GLException;
	
	int glGetRenderbufferParameteri(int target, int pname) throws GLException;
	
	float[] glGetSamplerParameter(int sampler, int pname, int argcount) throws GLException;
	
	int[] glGetSamplerParameteri(int sampler, int pname, int argcount) throws GLException;
	
	float glGetSamplerParameterf(int sampler, int pname) throws GLException;
	
	int glGetSamplerParameteri(int sampler, int pname) throws GLException;
	
	int[] glGetSamplerParameterI(int sampler, int pname, int argcount) throws GLException;
	
	int glGetSamplerParameterIi(int sampler, int pname) throws GLException;
	
	int[] glGetSamplerParameterIu(int sampler, int pname, int argcount) throws GLException;
	
	int glGetSamplerParameterIui(int sampler, int pname) throws GLException;
	
	String glGetStringi(int name, int index) throws GLException;
	
	int[] glGetSync(long sync, int pname, int[] length, int argcount) throws GLException;
	
	int glGetSynci(long sync, int pname) throws GLException;
	
	int[] glGetTexParameterI(int target, int pname, int argcount) throws GLException;
	
	int glGetTexParameterIi(int target, int pname) throws GLException;
	
	int[] glGetTexParameterIu(int target, int pname, int argcount) throws GLException;
	
	int glGetTexParameterIui(int target, int pname) throws GLException;
	
	String glGetTransformFeedbackVarying(int program, int index, int bufSize, int[] size, int[] type) throws GLException;
	
	int glGetUniformBlockIndex(int program, String uniformBlockName) throws GLException;
	
	int[] glGetUniformIndices(int program, String[] uniformNames) throws GLException;
	
	int glGetUniformLocation(int program, String name) throws GLException;
	
	int[] glGetUniformu(int program, int location, int argcount) throws GLException;
	
	double[] glGetVertexAttrib(int index, int pname, int argcount) throws GLException;
	
	float[] glGetVertexAttribf(int index, int pname, int argcount) throws GLException;
	
	int[] glGetVertexAttribI(int index, int pname, int argcount) throws GLException;
	
	int[] glGetVertexAttribIu(int index, int pname, int argcount) throws GLException;
	
	boolean glIsEnabledi(int target, int index) throws GLException;
	
	boolean glIsFramebuffer(int framebuffer);
	
	boolean glIsRenderbuffer(int renderbuffer);
	
	boolean glIsSampler(int sampler);
	
	boolean glIsSync(long sync);
	
	boolean glIsVertexArray(int array);
	
	ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer) throws GLException;
	
	void glPrimitiveRestartIndex(int index);
	
	void glProvokingVertex(int mode) throws GLException;
	
	void glQueryCounter(int id, int target) throws GLException;
	
	default void glRenderbufferStorage(int internalformat, int width, int height) throws GLException
	{
		this.glRenderbufferStorage(GLConst.GL_RENDERBUFFER, internalformat, width, height);
		
	}
	
	void glRenderbufferStorage(int target, int internalformat, int width, int height) throws GLException;
	
	void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) throws GLException;
	
	void glSampleMaski(int index, int mask) throws GLException;
	
	float[] glSamplerParameterf(int sampler, int pname, int argcount) throws GLException;
	
	int[] glSamplerParameter(int sampler, int pname, int argcount) throws GLException;
	
	void glSamplerParameterf(int sampler, int pname, float param) throws GLException;//TODO Check usage
	
	void glSamplerParameteri(int sampler, int pname, int param) throws GLException;;
	
	int[] glSamplerParameterI(int sampler, int pname, int argcount) throws GLException;
	
	int[] glSamplerParameterIu(int sampler, int pname, int argcount) throws GLException;
	
	void glTexBuffer(int target, int internalformat, int buffer) throws GLException;
	
	void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) throws GLException;
	
	void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) throws GLException;
	
	int[] glTexParameterI(int target, int pname, int argcount) throws GLException;
	
	void glTexParameterIi(int target, int pname, int param) throws GLException;
	
	int[] glTexParameterIu(int target, int pname, int argcount) throws GLException;
	
	void glTexParameterIui(int target, int pname, int param) throws GLException;
	
	void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode) throws GLException;
	
	void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) throws GLException;
	
	void glVertexAttribDivisor(int index, int divisor) throws GLException;
	
	void glVertexAttribI1(int index, int[] v) throws GLException;
	
	void glVertexAttribI1i(int index, int x) throws GLException;
	
	void glVertexAttribI1u(int index, int[] v) throws GLException;
	
	void glVertexAttribI1ui(int index, int x) throws GLException;
	
	void glVertexAttribI2(int index, int[] v) throws GLException;
	
	void glVertexAttribI2i(int index, int x, int y) throws GLException;
	
	void glVertexAttribI2u(int index, int[] v) throws GLException;
	
	void glVertexAttribI2ui(int index, int x, int y) throws GLException;
	
	void glVertexAttribI3(int index, int[] v) throws GLException;
	
	void glVertexAttribI3i(int index, int x, int y, int z) throws GLException;
	
	void glVertexAttribI3u(int index, int[] v) throws GLException;
	
	void glVertexAttribI3ui(int index, int x, int y, int z) throws GLException;
	
	void glVertexAttribI4(int index, int[] v) throws GLException;
	
	void glVertexAttribI4i(int index, int x, int y, int z, int w) throws GLException;
	
	void glVertexAttribI4u(int index, int[] v) throws GLException;
	
	void glVertexAttribI4ui(int index, int x, int y, int z, int w) throws GLException;
	
	void glVertexAttribIPointer(int index, int size, int type, int stride, int[] buffer) throws GLException;
	
	void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset) throws GLException;
	
	void glVertexAttribP1u(int index, int type, boolean normalized, int[] value) throws GLException;
	
	void glVertexAttribP1ui(int index, int type, boolean normalized, int value) throws GLException;
	
	void glVertexAttribP2u(int index, int type, boolean normalized, int[] value) throws GLException;
	
	void glVertexAttribP2ui(int index, int type, boolean normalized, int value) throws GLException;
	
	void glVertexAttribP3u(int index, int type, boolean normalized, int[] value) throws GLException;
	
	void glVertexAttribP3ui(int index, int type, boolean normalized, int value) throws GLException;
	
	void glVertexAttribP4u(int index, int type, boolean normalized, int[] value) throws GLException;
	
	void glVertexAttribP4ui(int index, int type, boolean normalized, int value) throws GLException;
	
	void glVertexP2u(int type, int[] value) throws GLException;
	
	void glVertexP2ui(int type, int value) throws GLException;
	
	void glVertexP3u(int type, int[] value) throws GLException;
	
	void glVertexP3ui(int type, int value) throws GLException;
	
	void glVertexP4u(int type, int[] value) throws GLException;
	
	void glVertexP4ui(int type, int value) throws GLException;
	
	void glWaitSync(long sync, int flags, long timeout) throws GLException;
	
}
