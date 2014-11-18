
package com.elusivehawk.caelum.render.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GL3
{
	private static IGL3 impl = null;
	
	private GL3(){}
	
	public static void setImpl(IGL3 gl)
	{
		assert impl == null;
		assert gl != null;
		
		impl = gl;
		
	}
	
	public static void glBeginConditionalRender(int id, int mode) throws GLException
	{
	}
	
	public static void glBeginTransformFeedback(int primitiveMode) throws GLException
	{
	}
	
	public static void glBindBufferBase(int target, int index, int buffer) throws GLException
	{
	}
	
	public static void glBindBufferRange(int target, int index, int buffer, long offset, long size) throws GLException
	{
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, String name) throws GLException
	{
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, String name) throws GLException
	{
	}
	
	public static void glBindFramebuffer(GLEnumFBType target, int framebuffer)
	{
		glBindFramebuffer(target.gl, framebuffer);
		
	}
	
	public static void glBindFramebuffer(int target, int framebuffer) throws GLException
	{
	}
	
	public static void glBindRenderbuffer(int renderbuffer) throws GLException
	{
		glBindRenderbuffer(GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	public static void glBindRenderbuffer(int target, int renderbuffer) throws GLException
	{
	}
	
	public static void glBindSampler(int unit, int sampler) throws GLException
	{
	}
	
	public static void glBindSamplers(int first, int argcount, IntBuffer samplers) throws GLException
	{
	}
	
	public static void glBindVertexArray(int array) throws GLException
	{
	}
	
	public static void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) throws GLException
	{
	}
	
	public static int glCheckFramebufferStatus(GLEnumFBType target) throws GLException
	{
		return glCheckFramebufferStatus(target.gl);
	}
	
	public static int glCheckFramebufferStatus(int target) throws GLException
	{
		return 0;
	}
	
	public static void glClampColor(int target, int clamp) throws GLException
	{
	}
	
	public static void glClearBuffer(int buffer, int drawbuffer, int type, Buffer value) throws GLException
	{
	}
	
	public static void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) throws GLException
	{
	}
	
	public static void glClearBufferu(int buffer, int drawbuffer, IntBuffer value) throws GLException
	{
	}
	
	public static int glClientWaitSync(long sync, int flags, long timeout) throws GLException
	{
		return 0;
	}
	
	public static void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a) throws GLException
	{
	}
	
	public static void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size) throws GLException
	{
	}
	
	public static void glDeleteFramebuffer(int framebuffer) throws GLException
	{
	}
	
	public static void glDeleteFramebuffers(int[] framebuffers) throws GLException
	{
	}
	
	public static void glDeleteRenderbuffer(int renderbuffer) throws GLException
	{
	}
	
	public static void glDeleteRenderbuffers(int[] renderbuffer) throws GLException
	{
	}
	
	public static void glDeleteSampler(int sampler) throws GLException
	{
	}
	
	public static void glDeleteSamplers(int[] samplers) throws GLException
	{
	}
	
	public static void glDeleteSync(long sync) throws GLException
	{
	}
	
	public static void glDeleteVertexArray(int array) throws GLException
	{
	}
	
	public static void glDeleteVertexArrays(int[] arrays) throws GLException
	{
	}
	
	public static void glDisablei(int target, int index) throws GLException
	{
	}
	
	public static void glDrawArraysInstanced(int mode, int first, int argcount, int primcount) throws GLException
	{
	}
	
	public static void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException
	{
	}
	
	public static void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset) throws GLException
	{
	}
	
	public static void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) throws GLException
	{
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex) throws GLException
	{
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException
	{
	}
	
	public static void glEnablei(int target, int index) throws GLException
	{
	}
	
	public static void glEndConditionalRender() throws GLException
	{
	}
	
	public static void glEndTransformFeedback() throws GLException
	{
	}
	
	public static long glFenceSync(int condition, int flags) throws GLException
	{
		return 0;
	}
	
	public static void glFlushMappedBufferRange(int target, long offset, long length) throws GLException
	{
	}
	
	public static void glFramebufferRenderbuffer(GLEnumFBType target, GLEnumFBAttach attachment, int renderbuffer) throws GLException
	{
		glFramebufferRenderbuffer(target, attachment, 0, renderbuffer);
		
	}
	
	public static void glFramebufferRenderbuffer(GLEnumFBType target, GLEnumFBAttach attachment, int count, int renderbuffer) throws GLException
	{
		glFramebufferRenderbuffer(target.gl, attachment.gl + count, GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	public static void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) throws GLException
	{
	}
	
	public static void glFramebufferTexture(GLEnumFBType target, GLEnumFBAttach attachment, int texture, int level) throws GLException
	{
		glFramebufferTexture(target, attachment, 0, texture, level);
		
	}
	
	public static void glFramebufferTexture(GLEnumFBType target, GLEnumFBAttach attachment, int count, int texture, int level) throws GLException
	{
		glFramebufferTexture(target.gl, attachment.gl + count, texture, level);
		
	}
	
	public static void glFramebufferTexture(int target, int attachment, int texture, int level) throws GLException
	{
	}
	
	public static void glFramebufferTexture1D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture1D(target, attachment, 0, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture1D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture1D(target.gl, attachment.gl + count, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level) throws GLException
	{
	}
	
	public static void glFramebufferTexture2D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture2D(target, attachment, 0, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture2D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level) throws GLException
	{
		glFramebufferTexture2D(target.gl, attachment.gl + count, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) throws GLException
	{
	}
	
	public static void glFramebufferTexture3D(GLEnumFBType target, GLEnumFBAttach attachment, int textarget, int texture, int level, int zoffset) throws GLException
	{
		glFramebufferTexture3D(target, attachment, 0, textarget, texture, level, zoffset);
		
	}
	
	public static void glFramebufferTexture3D(GLEnumFBType target, GLEnumFBAttach attachment, int count, int textarget, int texture, int level, int zoffset) throws GLException
	{
		glFramebufferTexture3D(target.gl, attachment.gl + count, textarget, texture, level, zoffset);
		
	}
	
	public static void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset) throws GLException
	{
	}
	
	public static void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) throws GLException
	{
	}
	
	public static void glGenerateMipmap(GLEnumTexture target) throws GLException
	{
		glGenerateMipmap(target.gl);
		
	}
	
	public static void glGenerateMipmap(int target) throws GLException
	{
	}
	
	public static int glGenFramebuffer() throws GLException
	{
		return 0;
	}
	
	public static int[] glGenFramebuffers(int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGenRenderbuffer() throws GLException
	{
		return 0;
	}
	
	public static int[] glGenRenderbuffers(int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGenSampler() throws GLException
	{
		return 0;
	}
	
	public static int[] glGenSamplers(int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGenVertexArray() throws GLException
	{
		return 0;
	}
	
	public static int[] glGenVertexArrays(int argcount) throws GLException
	{
		return null;
	}
	
	public static int[] glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname) throws GLException
	{
		return null;
	}
	
	public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize) throws GLException
	{
		return null;
	}
	
	public static String glGetActiveUniformName(int program, int uniformIndex, int bufSize) throws GLException
	{
		return null;
	}
	
	public static int[] glGetActiveUniforms(int program, int[] uniformIndices, int pname) throws GLException
	{
		return null;
	}
	
	public static int glGetActiveUniformsi(int program, int uniformIndex, int pname) throws GLException
	{
		return 0;
	}
	
	public static boolean glGetBoolean(int value, int index) throws GLException
	{
		return false;
	}
	
	public static boolean[] glGetBoolean(int value, int index, int argcount) throws GLException
	{
		return null;
	}
	
	public static long[] glGetBufferParameter(int target, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static long glGetBufferParameteri64(int target, int pname) throws GLException
	{
		return 0;
	}
	
	public static int glGetFragDataIndex(int program, String name) throws GLException
	{
		return 0;
	}
	
	public static int glGetFragDataLocation(int program, String name) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetFramebufferAttachmentParameter(int target, int attachment, int pname, int argcount)
	{
		return null;
	}
	
	public static int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname) throws GLException
	{
		return 0;
	}
	
	public static int glGetIntegeri(int value, int index) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetInteger(int value, int index, int argcount) throws GLException
	{
		return null;
	}
	
	public static long glGetInteger64i(int pname) throws GLException
	{
		return 0;
	}
	
	public static long glGetInteger64i(int value, int index) throws GLException
	{
		return 0;
	}
	
	public static long[] glGetInteger64(int value, int index, int argcount) throws GLException
	{
		return null;
	}
	
	public static long[] glGetInteger64(int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static float[] glGetMultisample(int pname, int index, int argcount) throws GLException
	{
		return null;
	}
	
	public static long[] glGetQueryObject(int id, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static long glGetQueryObjecti64(int id, int pname) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetQueryObjectu(int id, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static long[] glGetQueryObjectu64(int id, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static long glGetQueryObjectui64(int id, int pname) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetRenderbufferParameter(int target, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGetRenderbufferParameteri(int target, int pname) throws GLException
	{
		return 0;
	}
	
	public static float[] glGetSamplerParameter(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int[] glGetSamplerParameteri(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static float glGetSamplerParameterf(int sampler, int pname) throws GLException
	{
		return 0;
	}
	
	public static int glGetSamplerParameteri(int sampler, int pname) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetSamplerParameterI(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGetSamplerParameterIi(int sampler, int pname) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetSamplerParameterIu(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGetSamplerParameterIui(int sampler, int pname) throws GLException
	{
		return 0;
	}
	
	public static String glGetStringi(int name, int index) throws GLException
	{
		return null;
	}
	
	public static int[] glGetSync(long sync, int pname, int[] length, int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGetSynci(long sync, int pname) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetTexParameterI(int target, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGetTexParameterIi(int target, int pname) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetTexParameterIu(int target, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int glGetTexParameterIui(int target, int pname) throws GLException
	{
		return 0;
	}
	
	public static String glGetTransformFeedbackVarying(int program, int index, int bufSize, int[] size, int[] type) throws GLException
	{
		return null;
	}
	
	public static int glGetUniformBlockIndex(int program, String uniformBlockName) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetUniformIndices(int program, String[] uniformNames) throws GLException
	{
		return null;
	}
	
	public static int glGetUniformLocation(int program, String name) throws GLException
	{
		return 0;
	}
	
	public static int[] glGetUniformu(int program, int location, int argcount) throws GLException
	{
		return null;
	}
	
	public static double[] glGetVertexAttrib(int index, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static float[] glGetVertexAttribf(int index, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int[] glGetVertexAttribI(int index, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int[] glGetVertexAttribIu(int index, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static boolean glIsEnabledi(int target, int index) throws GLException
	{
		return false;
	}
	
	public static boolean glIsFramebuffer(int framebuffer)
	{
		return false;
	}
	
	public static boolean glIsRenderbuffer(int renderbuffer)
	{
		return false;
	}
	
	public static boolean glIsSampler(int sampler)
	{
		return false;
	}
	
	public static boolean glIsSync(long sync)
	{
		return false;
	}
	
	public static boolean glIsVertexArray(int array)
	{
		return false;
	}
	
	public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer) throws GLException
	{
		return null;
	}
	
	public static void glPrimitiveRestartIndex(int index)
	{
	}
	
	public static void glProvokingVertex(int mode) throws GLException
	{
	}
	
	public static void glQueryCounter(int id, int target) throws GLException
	{
	}
	
	public static void glRenderbufferStorage(int internalformat, int width, int height) throws GLException
	{
		glRenderbufferStorage(GLConst.GL_RENDERBUFFER, internalformat, width, height);
		
	}
	
	public static void glRenderbufferStorage(int target, int internalformat, int width, int height) throws GLException
	{
	}
	
	public static void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) throws GLException
	{
	}
	
	public static void glSampleMaski(int index, int mask) throws GLException
	{
	}
	
	public static float[] glSamplerParameterf(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int[] glSamplerParameter(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static void glSamplerParameterf(int sampler, int pname, float param) throws GLException
	{
	}//TODO Check usage
	
	public static void glSamplerParameteri(int sampler, int pname, int param) throws GLException
	{
	}
	
	public static int[] glSamplerParameterI(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static int[] glSamplerParameterIu(int sampler, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static void glTexBuffer(int target, int internalformat, int buffer) throws GLException
	{
	}
	
	public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) throws GLException
	{
	}
	
	public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) throws GLException
	{
	}
	
	public static int[] glTexParameterI(int target, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static void glTexParameterIi(int target, int pname, int param) throws GLException
	{
	}
	
	public static int[] glTexParameterIu(int target, int pname, int argcount) throws GLException
	{
		return null;
	}
	
	public static void glTexParameterIui(int target, int pname, int param) throws GLException
	{
	}
	
	public static void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode) throws GLException
	{
	}
	
	public static void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) throws GLException
	{
	}
	
	public static void glVertexAttribDivisor(int index, int divisor) throws GLException
	{
	}
	
	public static void glVertexAttribI1(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI1i(int index, int x) throws GLException
	{
	}
	
	public static void glVertexAttribI1u(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI1ui(int index, int x) throws GLException
	{
	}
	
	public static void glVertexAttribI2(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI2i(int index, int x, int y) throws GLException
	{
	}
	
	public static void glVertexAttribI2u(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI2ui(int index, int x, int y) throws GLException
	{
	}
	
	public static void glVertexAttribI3(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI3i(int index, int x, int y, int z) throws GLException
	{
	}
	
	public static void glVertexAttribI3u(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI3ui(int index, int x, int y, int z) throws GLException
	{
	}
	
	public static void glVertexAttribI4(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI4i(int index, int x, int y, int z, int w) throws GLException
	{
	}
	
	public static void glVertexAttribI4u(int index, int[] v) throws GLException
	{
	}
	
	public static void glVertexAttribI4ui(int index, int x, int y, int z, int w) throws GLException
	{
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, int[] buffer) throws GLException
	{
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset) throws GLException
	{
	}
	
	public static void glVertexAttribP1u(int index, int type, boolean normalized, int[] value) throws GLException
	{
	}
	
	public static void glVertexAttribP1ui(int index, int type, boolean normalized, int value) throws GLException
	{
	}
	
	public static void glVertexAttribP2u(int index, int type, boolean normalized, int[] value) throws GLException
	{
	}
	
	public static void glVertexAttribP2ui(int index, int type, boolean normalized, int value) throws GLException
	{
	}
	
	public static void glVertexAttribP3u(int index, int type, boolean normalized, int[] value) throws GLException
	{
	}
	
	public static void glVertexAttribP3ui(int index, int type, boolean normalized, int value) throws GLException
	{
	}
	
	public static void glVertexAttribP4u(int index, int type, boolean normalized, int[] value) throws GLException
	{
	}
	
	public static void glVertexAttribP4ui(int index, int type, boolean normalized, int value) throws GLException
	{
	}
	
	public static void glVertexP2u(int type, int[] value) throws GLException
	{
	}
	
	public static void glVertexP2ui(int type, int value) throws GLException
	{
	}
	
	public static void glVertexP3u(int type, int[] value) throws GLException
	{
	}
	
	public static void glVertexP3ui(int type, int value) throws GLException
	{
	}
	
	public static void glVertexP4u(int type, int[] value) throws GLException
	{
	}
	
	public static void glVertexP4ui(int type, int value) throws GLException
	{
	}
	
	public static void glWaitSync(long sync, int flags, long timeout) throws GLException
	{
	}
	
}
