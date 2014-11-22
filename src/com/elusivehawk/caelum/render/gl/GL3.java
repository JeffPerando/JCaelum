
package com.elusivehawk.caelum.render.gl;

import java.nio.ByteBuffer;
import com.elusivehawk.caelum.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GL3
{
	private static IGL3Impl impl = null;
	
	private GL3(){}
	
	public static void setImpl(IGL3Impl gl)
	{
		assert impl == null;
		assert gl != null;
		
		impl = gl;
		
	}
	
	public static void glBeginConditionalRender(int id, int mode) throws GLException
	{
		impl.glBeginConditionalRender(id, mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBeginTransformFeedback(int primitiveMode) throws GLException
	{
		impl.glBeginTransformFeedback(primitiveMode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindBufferBase(int target, int index, int buffer) throws GLException
	{
		impl.glBindBufferBase(target, index, buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindBufferRange(int target, int index, int buffer, long offset, long size) throws GLException
	{
		impl.glBindBufferRange(target, index, buffer, offset, size);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, String name) throws GLException
	{
		impl.glBindFragDataLocation(program, colorNumber, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, String name) throws GLException
	{
		impl.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindFramebuffer(GLEnumFBType target, int framebuffer)
	{
		glBindFramebuffer(target.gl, framebuffer);
		
	}
	
	public static void glBindFramebuffer(int target, int framebuffer) throws GLException
	{
		impl.glBindFramebuffer(target, framebuffer);
		
	}
	
	public static void glBindRenderbuffer(int renderbuffer) throws GLException
	{
		glBindRenderbuffer(GLConst.GL_RENDERBUFFER, renderbuffer);
		
	}
	
	public static void glBindRenderbuffer(int target, int renderbuffer) throws GLException
	{
		impl.glBindRenderbuffer(target, renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindSampler(int unit, int sampler) throws GLException
	{
		impl.glBindSampler(unit, sampler);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindVertexArray(int array) throws GLException
	{
		impl.glBindVertexArray(array);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) throws GLException
	{
		impl.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glCheckFramebufferStatus(GLEnumFBType target) throws GLException
	{
		return glCheckFramebufferStatus(target.gl);
	}
	
	public static int glCheckFramebufferStatus(int target) throws GLException
	{
		int ret = impl.glCheckFramebufferStatus(target);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glClampColor(int target, int clamp) throws GLException
	{
		impl.glClampColor(target, clamp);
		
	}
	
	public static void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) throws GLException
	{
		impl.glClearBufferfi(buffer, drawbuffer, depth, stencil);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glClientWaitSync(long sync, int flags, long timeout) throws GLException
	{
		int ret = impl.glClientWaitSync(sync, flags, timeout);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a) throws GLException
	{
		impl.glColorMaski(buf, r, g, b, a);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size) throws GLException
	{
		impl.glCopyBufferSubData(readtarget, writetarget, readoffset, writeoffset, size);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteFramebuffer(int framebuffer) throws GLException
	{
		impl.glDeleteFramebuffer(framebuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteFramebuffers(int[] framebuffers) throws GLException
	{
		impl.glDeleteFramebuffers(framebuffers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteRenderbuffer(int renderbuffer) throws GLException
	{
		impl.glDeleteRenderbuffer(renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteRenderbuffers(int[] renderbuffer) throws GLException
	{
		impl.glDeleteRenderbuffers(renderbuffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteSampler(int sampler) throws GLException
	{
		impl.glDeleteSampler(sampler);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteSamplers(int[] samplers) throws GLException
	{
		impl.glDeleteSamplers(samplers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteSync(long sync) throws GLException
	{
		impl.glDeleteSync(sync);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteVertexArray(int array) throws GLException
	{
		impl.glDeleteVertexArray(array);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteVertexArrays(int[] arrays) throws GLException
	{
		impl.glDeleteVertexArrays(arrays);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDisablei(int target, int index) throws GLException
	{
		impl.glDisablei(target, index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawArraysInstanced(int mode, int first, int argcount, int primcount) throws GLException
	{
		impl.glDrawArraysInstanced(mode, first, argcount, primcount);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException
	{
		impl.glDrawElementsBaseVertex(mode, indices_count, type, indices_buffer_offset, basevertex);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset) throws GLException
	{
		impl.glDrawElementsIndirect(mode, type, indirect_buffer_offset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) throws GLException
	{
		impl.glDrawElementsInstanced(mode, indices_count, type, indices_buffer_offset, primcount);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex) throws GLException
	{
		impl.glDrawElementsInstancedBaseVertex(mode, indices_count, type, indices_buffer_offset, primcount, basevertex);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex) throws GLException
	{
		impl.glDrawRangeElementsBaseVertex(mode, start, end, indices_count, type, indices_buffer_offset, basevertex);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEnablei(int target, int index) throws GLException
	{
		impl.glEnablei(target, index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEndConditionalRender() throws GLException
	{
		impl.glEndConditionalRender();
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEndTransformFeedback() throws GLException
	{
		impl.glEndTransformFeedback();
		
		RenderHelper.checkForGLError();
		
	}
	
	public static long glFenceSync(int condition, int flags) throws GLException
	{
		long ret = impl.glFenceSync(condition, flags);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glFlushMappedBufferRange(int target, long offset, long length) throws GLException
	{
		impl.glFlushMappedBufferRange(target, offset, length);
		
		RenderHelper.checkForGLError();
		
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
		impl.glFramebufferTexture(target, attachment, texture, level);
		
		RenderHelper.checkForGLError();
		
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
		impl.glFramebufferTexture1D(target, attachment, textarget, texture, level);
		
		RenderHelper.checkForGLError();
		
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
		impl.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		
		RenderHelper.checkForGLError();
		
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
		impl.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) throws GLException
	{
		impl.glFramebufferTextureLayer(target, attachment, texture, level, layer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGenerateMipmap(GLEnumTexture target) throws GLException
	{
		glGenerateMipmap(target.gl);
		
	}
	
	public static void glGenerateMipmap(int target) throws GLException
	{
		impl.glGenerateMipmap(target);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGenFramebuffer() throws GLException
	{
		int ret = impl.glGenFramebuffer();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGenFramebuffers(int argcount) throws GLException
	{
		int[] ret = impl.glGenFramebuffers(argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenRenderbuffer() throws GLException
	{
		int ret = impl.glGenRenderbuffer();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGenRenderbuffers(int argcount) throws GLException
	{
		int[] ret = impl.glGenRenderbuffers(argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenSampler() throws GLException
	{
		int ret = impl.glGenSampler();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGenSamplers(int argcount) throws GLException
	{
		int[] ret = impl.glGenSamplers(argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenVertexArray() throws GLException
	{
		int ret = impl.glGenVertexArray();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGenVertexArrays(int argcount) throws GLException
	{
		int[] ret = impl.glGenVertexArrays(argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize) throws GLException
	{
		String ret = impl.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveUniformName(int program, int uniformIndex, int bufSize) throws GLException
	{
		String ret = impl.glGetActiveUniformName(program, uniformIndex, bufSize);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetActiveUniforms(int program, int[] uniformIndices, int pname) throws GLException
	{
		int[] ret = impl.glGetActiveUniforms(program, uniformIndices, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetActiveUniformsi(int program, int uniformIndex, int pname) throws GLException
	{
		int ret = impl.glGetActiveUniformsi(program, uniformIndex, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static boolean glGetBoolean(int value, int index) throws GLException
	{
		boolean ret = impl.glGetBoolean(value, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static boolean[] glGetBoolean(int value, int index, int argcount) throws GLException
	{
		boolean[] ret = impl.glGetBoolean(value, index, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long[] glGetBufferParameter(int target, int pname, int argcount) throws GLException
	{
		long[] ret = impl.glGetBufferParameter(target, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetBufferParameteri64(int target, int pname) throws GLException
	{
		long ret = impl.glGetBufferParameteri64(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetFragDataIndex(int program, String name) throws GLException
	{
		int ret = impl.glGetFragDataIndex(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetFragDataLocation(int program, String name) throws GLException
	{
		int ret = impl.glGetFragDataLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetFramebufferAttachmentParameter(int target, int attachment, int pname, int argcount)
	{
		int[] ret = impl.glGetFramebufferAttachmentParameter(target, attachment, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname) throws GLException
	{
		int ret = impl.glGetFramebufferAttachmentParameteri(target, attachment, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetIntegeri(int value, int index) throws GLException
	{
		int ret = impl.glGetIntegeri(value, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetInteger(int value, int index, int argcount) throws GLException
	{
		int[] ret = impl.glGetInteger(value, index, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetInteger64i(int pname) throws GLException
	{
		long ret = impl.glGetInteger64i(pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetInteger64i(int value, int index) throws GLException
	{
		long ret = impl.glGetInteger64i(value, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long[] glGetInteger64(int value, int index, int argcount) throws GLException
	{
		long[] ret = impl.glGetInteger64(value, index, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long[] glGetInteger64(int pname, int argcount) throws GLException
	{
		long[] ret = impl.glGetInteger64(pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static float[] glGetMultisample(int pname, int index, int argcount) throws GLException
	{
		float[] ret = impl.glGetMultisample(pname, index, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long[] glGetQueryObject(int id, int pname, int argcount) throws GLException
	{
		long[] ret = impl.glGetQueryObject(id, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetQueryObjecti64(int id, int pname) throws GLException
	{
		long ret = impl.glGetQueryObjecti64(id, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static long glGetQueryObjectui64(int id, int pname) throws GLException
	{
		long ret = impl.glGetQueryObjectui64(id, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetRenderbufferParameter(int target, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetRenderbufferParameter(target, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetRenderbufferParameteri(int target, int pname) throws GLException
	{
		int ret = impl.glGetRenderbufferParameteri(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static float[] glGetSamplerParameter(int sampler, int pname, int argcount) throws GLException
	{
		float[] ret = impl.glGetSamplerParameter(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetSamplerParameteri(int sampler, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetSamplerParameteri(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static float glGetSamplerParameterf(int sampler, int pname) throws GLException
	{
		float ret = impl.glGetSamplerParameterf(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSamplerParameteri(int sampler, int pname) throws GLException
	{
		int ret = impl.glGetSamplerParameteri(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetSamplerParameterI(int sampler, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetSamplerParameterI(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSamplerParameterIi(int sampler, int pname) throws GLException
	{
		int ret = impl.glGetSamplerParameterIi(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetSamplerParameterIu(int sampler, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetSamplerParameterIu(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSamplerParameterIui(int sampler, int pname) throws GLException
	{
		int ret = impl.glGetSamplerParameterIui(sampler, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetStringi(int name, int index) throws GLException
	{
		String ret = impl.glGetStringi(name, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetSync(long sync, int pname, int[] length, int argcount) throws GLException
	{
		int[] ret = impl.glGetSync(sync, pname, length, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetSynci(long sync, int pname) throws GLException
	{
		int ret = impl.glGetSynci(sync, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetTexParameterI(int target, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetTexParameterI(target, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetTexParameterIi(int target, int pname) throws GLException
	{
		int ret = impl.glGetTexParameterIi(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetTexParameterIu(int target, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetTexParameterIu(target, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetTexParameterIui(int target, int pname) throws GLException
	{
		int ret = impl.glGetTexParameterIui(target, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetTransformFeedbackVarying(int program, int index, int bufSize, int[] size, int[] type) throws GLException
	{
		String ret = impl.glGetTransformFeedbackVarying(program, index, bufSize, size, type);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetUniformBlockIndex(int program, String uniformBlockName) throws GLException
	{
		int ret = impl.glGetUniformBlockIndex(program, uniformBlockName);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetUniformIndices(int program, String[] uniformNames) throws GLException
	{
		int[] ret = impl.glGetUniformIndices(program, uniformNames);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetUniformLocation(int program, String name) throws GLException
	{
		int ret = impl.glGetUniformLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetUniformu(int program, int location, int argcount) throws GLException
	{
		int[] ret = impl.glGetUniformu(program, location, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetVertexAttribI(int index, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetVertexAttribI(index, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glGetVertexAttribIu(int index, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glGetVertexAttribIu(index, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static boolean glIsEnabledi(int target, int index) throws GLException
	{
		return impl.glIsEnabledi(target, index);
	}
	
	public static boolean glIsFramebuffer(int framebuffer)
	{
		return impl.glIsFramebuffer(framebuffer);
	}
	
	public static boolean glIsRenderbuffer(int renderbuffer)
	{
		return impl.glIsRenderbuffer(renderbuffer);
	}
	
	public static boolean glIsSampler(int sampler)
	{
		return impl.glIsSampler(sampler);
	}
	
	public static boolean glIsSync(long sync)
	{
		return impl.glIsSync(sync);
	}
	
	public static boolean glIsVertexArray(int array)
	{
		return impl.glIsVertexArray(array);
	}
	
	public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer) throws GLException
	{
		ByteBuffer ret = impl.glMapBufferRange(target, offset, length, access, old_buffer);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glPrimitiveRestartIndex(int index)
	{
		impl.glPrimitiveRestartIndex(index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glProvokingVertex(int mode) throws GLException
	{
		impl.glProvokingVertex(mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glQueryCounter(int id, int target) throws GLException
	{
		impl.glQueryCounter(id, target);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glRenderbufferStorage(int internalformat, int width, int height) throws GLException
	{
		glRenderbufferStorage(GLConst.GL_RENDERBUFFER, internalformat, width, height);
		
	}
	
	public static void glRenderbufferStorage(int target, int internalformat, int width, int height) throws GLException
	{
		impl.glRenderbufferStorage(target, internalformat, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) throws GLException
	{
		impl.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glSampleMaski(int index, int mask) throws GLException
	{
		impl.glSampleMaski(index, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static float[] glSamplerParameterf(int sampler, int pname, int argcount) throws GLException
	{
		float[] ret = impl.glSamplerParameterf(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glSamplerParameter(int sampler, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glSamplerParameter(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glSamplerParameterf(int sampler, int pname, float param) throws GLException
	{
		impl.glSamplerParameterf(sampler, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glSamplerParameteri(int sampler, int pname, int param) throws GLException
	{
		impl.glSamplerParameteri(sampler, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int[] glSamplerParameterI(int sampler, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glSamplerParameterI(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int[] glSamplerParameterIu(int sampler, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glSamplerParameterIu(sampler, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glTexBuffer(int target, int internalformat, int buffer) throws GLException
	{
		impl.glTexBuffer(target, internalformat, buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) throws GLException
	{
		impl.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) throws GLException
	{
		impl.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int[] glTexParameterI(int target, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glTexParameterI(target, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glTexParameterIi(int target, int pname, int param) throws GLException
	{
		impl.glTexParameterIi(target, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int[] glTexParameterIu(int target, int pname, int argcount) throws GLException
	{
		int[] ret = impl.glTexParameterIu(target, pname, argcount);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glTexParameterIui(int target, int pname, int param) throws GLException
	{
		impl.glTexParameterIui(target, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode) throws GLException
	{
		impl.glTransformFeedbackVaryings(program, varyings, bufferMode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) throws GLException
	{
		impl.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribDivisor(int index, int divisor) throws GLException
	{
		impl.glVertexAttribDivisor(index, divisor);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI1(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1i(int index, int x) throws GLException
	{
		impl.glVertexAttribI1i(index, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1u(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI1u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI1ui(int index, int x) throws GLException
	{
		impl.glVertexAttribI1ui(index, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI2(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2i(int index, int x, int y) throws GLException
	{
		impl.glVertexAttribI2i(index, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2u(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI2u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI2ui(int index, int x, int y) throws GLException
	{
		impl.glVertexAttribI2ui(index, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI3(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3i(int index, int x, int y, int z) throws GLException
	{
		impl.glVertexAttribI3i(index, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3u(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI3u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI3ui(int index, int x, int y, int z) throws GLException
	{
		impl.glVertexAttribI3ui(index, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI4(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4i(int index, int x, int y, int z, int w) throws GLException
	{
		impl.glVertexAttribI4i(index, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4u(int index, int[] v) throws GLException
	{
		impl.glVertexAttribI4u(index, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribI4ui(int index, int x, int y, int z, int w) throws GLException
	{
		impl.glVertexAttribI4ui(index, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, int[] buffer) throws GLException
	{
		impl.glVertexAttribIPointer(index, size, type, stride, buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset) throws GLException
	{
		impl.glVertexAttribIPointer(index, size, type, stride, buffer_buffer_offset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP1u(int index, int type, boolean normalized, int[] value) throws GLException
	{
		impl.glVertexAttribP1u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP1ui(int index, int type, boolean normalized, int value) throws GLException
	{
		impl.glVertexAttribP1ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP2u(int index, int type, boolean normalized, int[] value) throws GLException
	{
		impl.glVertexAttribP2u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP2ui(int index, int type, boolean normalized, int value) throws GLException
	{
		impl.glVertexAttribP2ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP3u(int index, int type, boolean normalized, int[] value) throws GLException
	{
		impl.glVertexAttribP3u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP3ui(int index, int type, boolean normalized, int value) throws GLException
	{
		impl.glVertexAttribP3ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP4u(int index, int type, boolean normalized, int[] value) throws GLException
	{
		impl.glVertexAttribP4u(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribP4ui(int index, int type, boolean normalized, int value) throws GLException
	{
		impl.glVertexAttribP4ui(index, type, normalized, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP2u(int type, int[] value) throws GLException
	{
		impl.glVertexP2u(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP2ui(int type, int value) throws GLException
	{
		impl.glVertexP2ui(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP3u(int type, int[] value) throws GLException
	{
		impl.glVertexP3u(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP3ui(int type, int value) throws GLException
	{
		impl.glVertexP3ui(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP4u(int type, int[] value) throws GLException
	{
		impl.glVertexP4u(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexP4ui(int type, int value) throws GLException
	{
		impl.glVertexP4ui(type, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glWaitSync(long sync, int flags, long timeout) throws GLException
	{
		impl.glWaitSync(sync, flags, timeout);
		
		RenderHelper.checkForGLError();
		
	}
	
}
