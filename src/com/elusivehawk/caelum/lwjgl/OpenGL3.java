
package com.elusivehawk.caelum.lwjgl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL40;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.IGL3Impl;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL3 implements IGL3Impl
{
	@Override
	public void glBeginConditionalRender(int id, int mode)
	{
		GL30.glBeginConditionalRender(id, mode);
		
	}
	
	@Override
	public void glBeginTransformFeedback(int primitiveMode)
	{
		GL30.glBeginTransformFeedback(primitiveMode);
		
	}
	
	@Override
	public void glBindBufferBase(int target, int index, int buffer)
	{
		GL30.glBindBufferBase(target, index, buffer);
		
	}
	
	@Override
	public void glBindBufferRange(int target, int index, int buffer, long offset, long size)
	{
		GL30.glBindBufferRange(target, index, buffer, offset, size);
		
	}
	
	@Override
	public void glBindFragDataLocation(int program, int colorNumber, String name)
	{
		GL30.glBindFragDataLocation(program, colorNumber, name);
		
	}
	
	@Override
	public void glBindFragDataLocationIndexed(int program, int colorNumber, int index, String name)
	{
		GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
	}
	
	@Override
	public void glBindFramebuffer(int target, int framebuffer)
	{
		GL30.glBindFramebuffer(target, framebuffer);
		
	}
	
	@Override
	public void glBindRenderbuffer(int target, int renderbuffer)
	{
		GL30.glBindRenderbuffer(target, renderbuffer);
		
	}
	
	@Override
	public void glBindSampler(int unit, int sampler)
	{
		GL33.glBindSampler(unit, sampler);
		
	}
	
	@Override
	public void glBindVertexArray(int array)
	{
		GL30.glBindVertexArray(array);
		
	}
	
	@Override
	public void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter)
	{
		GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		
	}
	
	@Override
	public int glCheckFramebufferStatus(int target)
	{
		return GL30.glCheckFramebufferStatus(target);
	}
	
	@Override
	public void glClampColor(int target, int clamp)
	{
		GL30.glClampColor(target, clamp);
		
	}
	
	@Override
	public void glClearBuffer(int buffer, int drawbuffer, FloatBuffer value)
	{
		GL30.glClearBuffer(buffer, drawbuffer, value);
		
	}
	
	@Override
	public void glClearBuffer(int buffer, int drawbuffer, IntBuffer value)
	{
		GL30.glClearBuffer(buffer, drawbuffer, value);
		
	}
	
	@Override
	public void glClearBufferf(int buffer, int drawbuffer, ByteBuffer value)
	{
		GL30.glClearBufferf(buffer, drawbuffer, value);
		
	}
	
	@Override
	public void glClearBufferi(int buffer, int drawbuffer, ByteBuffer value)
	{
		GL30.glClearBufferi(buffer, drawbuffer, value);
		
	}
	
	@Override
	public void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil)
	{
		GL30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
		
	}
	
	@Override
	public int glClientWaitSync(long sync, int flags, long timeout)
	{
		return GL32.glClientWaitSync(sync, flags, timeout);
	}
	
	@Override
	public void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a)
	{
		GL30.glColorMaski(buf, r, g, b, a);
		
	}
	
	@Override
	public void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size)
	{
		GL31.glCopyBufferSubData(readtarget, writetarget, readoffset, writeoffset, size);
		
	}
	
	@Override
	public void glDeleteFramebuffer(int framebuffer)
	{
		GL30.glDeleteFramebuffers(framebuffer);
		
	}
	
	@Override
	public void glDeleteFramebuffers(int[] framebuffers)
	{
		GL30.glDeleteFramebuffers(BufferHelper.createWrapper(framebuffers));
		
	}
	
	@Override
	public void glDeleteRenderbuffer(int renderbuffer)
	{
		GL30.glDeleteRenderbuffers(renderbuffer);
		
	}
	
	@Override
	public void glDeleteRenderbuffers(int[] renderbuffer)
	{
		GL30.glDeleteRenderbuffers(BufferHelper.createWrapper(renderbuffer));
		
	}
	
	@Override
	public void glDeleteSampler(int sampler)
	{
		GL33.glDeleteSamplers(sampler);
		
	}
	
	@Override
	public void glDeleteSamplers(int[] samplers)
	{
		GL33.glDeleteSamplers(BufferHelper.createWrapper(samplers));
		
	}
	
	@Override
	public void glDeleteSync(long sync)
	{
		GL32.glDeleteSync(sync);
		
	}
	
	@Override
	public void glDeleteVertexArray(int array)
	{
		GL30.glDeleteVertexArrays(array);
		
	}
	
	@Override
	public void glDeleteVertexArrays(int[] arrays)
	{
		GL30.glDeleteVertexArrays(BufferHelper.createWrapper(arrays));
		
	}
	
	@Override
	public void glDisablei(int target, int index)
	{
		GL30.glDisablei(target, index);
		
	}
	
	@Override
	public void glDrawArraysInstanced(int mode, int first, int argcount, int primcount)
	{
		GL31.glDrawArraysInstanced(mode, first, argcount, primcount);
		
	}
	
	@Override
	public void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices_count, type, indices_buffer_offset, basevertex);
		
	}
	
	@Override
	public void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect_buffer_offset);
		
	}
	
	@Override
	public void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices_count, type, indices_buffer_offset, primcount);
		
		
	}
	
	@Override
	public void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices_count, type, indices_buffer_offset, basevertex);
		
	}
	
	@Override
	public void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices_count, type, indices_buffer_offset, basevertex);
		
		
	}
	
	@Override
	public void glEnablei(int target, int index)
	{
		GL30.glEnablei(target, index);
		
	}
	
	@Override
	public void glEndConditionalRender()
	{
		GL30.glEndConditionalRender();
		
	}
	
	@Override
	public void glEndTransformFeedback()
	{
		GL30.glEndTransformFeedback();
		
	}
	
	@Override
	public long glFenceSync(int condition, int flags)
	{
		return GL32.glFenceSync(condition, flags);
	}
	
	@Override
	public void glFlushMappedBufferRange(int target, long offset, long length)
	{
		GL30.glFlushMappedBufferRange(target, offset, length);
		
	}
	
	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer)
	{
		GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
		
	}
	
	@Override
	public void glFramebufferTexture(int target, int attachment, int texture, int level)
	{
		GL32.glFramebufferTexture(target, attachment, texture, level);
		
	}
	
	@Override
	public void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level)
	{
		GL30.glFramebufferTexture1D(target, attachment, textarget, texture, level);
		
	}
	
	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level)
	{
		GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		
	}
	
	@Override
	public void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset)
	{
		GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
		
	}
	
	@Override
	public void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer)
	{
		GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
		
	}
	
	@Override
	public void glGenerateMipmap(int target)
	{
		GL30.glGenerateMipmap(target);
		
	}
	
	@Override
	public int glGenFramebuffer()
	{
		return GL30.glGenFramebuffers();
	}
	
	@Override
	public int[] glGenFramebuffers(int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGenFramebuffers(BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGenRenderbuffer()
	{
		return GL30.glGenRenderbuffers();
	}
	
	@Override
	public int[] glGenRenderbuffers(int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGenRenderbuffers(BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGenSampler()
	{
		return GL33.glGenSamplers();
	}
	
	@Override
	public int[] glGenSamplers(int argcount)
	{
		int[] ret = new int[argcount];
		
		GL33.glGenSamplers(BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGenVertexArray()
	{
		return GL30.glGenVertexArrays();
	}

	@Override
	public int[] glGenVertexArrays(int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGenVertexArrays(BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname)
	{
		return GL31.glGetActiveUniformBlocki(program, uniformBlockIndex, pname);
	}
	
	@Override
	public String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize)
	{
		return GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
	}
	
	@Override
	public String glGetActiveUniformName(int program, int uniformIndex, int bufSize)
	{
		return GL31.glGetActiveUniformName(program, uniformIndex, bufSize);
	}
	
	@Override
	public int[] glGetActiveUniforms(int program, int[] uniformIndices, int pname)//TODO Investigate usage
	{
		int[] ret = new int[uniformIndices.length];
		
		GL31.glGetActiveUniforms(program, BufferHelper.createWrapper(uniformIndices), pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetActiveUniformsi(int program, int uniformIndex, int pname)
	{
		return GL31.glGetActiveUniformsi(program, uniformIndex, pname);
	}
	
	@Override
	public boolean glGetBoolean(int value, int index)
	{
		return GL30.glGetBooleani(value, index);
	}
	
	@Override
	public boolean[] glGetBoolean(int value, int index, int argcount)
	{
		boolean[] ret = new boolean[argcount];
		byte[] bs = new byte[argcount];
		
		GL30.glGetBooleani(value, index, BufferHelper.createWrapper(bs));
		
		for (int c = 0; c < argcount; c++)
		{
			ret[c] = (bs[c] == GLConst.GL_TRUE);
			
		}
		
		return ret;
	}
	
	@Override
	public long[] glGetBufferParameter(int target, int pname, int argcount)
	{
		long[] ret = new long[argcount];
		
		GL32.glGetBufferParameter(target, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public long glGetBufferParameteri64(int target, int pname)
	{
		return GL32.glGetBufferParameteri64(target, pname);
	}
	
	@Override
	public int glGetFragDataIndex(int program, String name)
	{
		return GL33.glGetFragDataIndex(program, name);
	}
	
	@Override
	public int glGetFragDataLocation(int program, String name)
	{
		return GL30.glGetFragDataLocation(program, name);
	}
	
	@Override
	public int[] glGetFramebufferAttachmentParameter(int target, int attachment, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGetFramebufferAttachmentParameter(target, attachment, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname)
	{
		return GL30.glGetFramebufferAttachmentParameteri(target, attachment, pname);
	}
	
	@Override
	public int glGetIntegeri(int value, int index)
	{
		return GL30.glGetIntegeri(value, index);
	}
	
	@Override
	public int[] glGetInteger(int value, int index, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGetInteger(value, index, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public long glGetInteger64i(int pname)
	{
		return GL32.glGetInteger64(pname);
	}
	
	@Override
	public long glGetInteger64i(int value, int index)
	{
		return GL32.glGetInteger64i(value, index);
	}
	
	@Override
	public long[] glGetInteger64(int value, int index, int argcount)
	{
		long[] ret = new long[argcount];
		
		GL32.glGetInteger64i(value, index, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public long[] glGetInteger64(int pname, int argcount)
	{
		long[] ret = new long[argcount];
		
		GL32.glGetInteger64(pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public float[] glGetMultisample(int pname, int index, int argcount)
	{
		float[] ret = new float[argcount];
		
		GL32.glGetMultisample(pname, index, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public long[] glGetQueryObject(int id, int pname, int argcount)
	{
		long[] ret = new long[argcount];
		
		GL33.glGetQueryObject(id, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public long glGetQueryObjecti64(int id, int pname)
	{
		return GL33.glGetQueryObjecti64(id, pname);
	}
	
	@Override
	public long[] glGetQueryObjectu(int id, int pname, int argcount)
	{
		long[] ret = new long[argcount];
		
		GL33.glGetQueryObjectu(id, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public long glGetQueryObjectui64(int id, int pname)
	{
		return GL33.glGetQueryObjectui64(id, pname);
	}
	
	@Override
	public int[] glGetRenderbufferParameter(int target, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGetRenderbufferParameter(target, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetRenderbufferParameteri(int target, int pname)
	{
		return GL30.glGetRenderbufferParameteri(target, pname);
	}
	
	@Override
	public float[] glGetSamplerParameter(int sampler, int pname, int argcount)
	{
		float[] ret = new float[argcount];
		
		GL33.glGetSamplerParameter(sampler, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int[] glGetSamplerParameteri(int sampler, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL33.glGetSamplerParameter(sampler, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public float glGetSamplerParameterf(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterf(sampler, pname);
	}
	
	@Override
	public int glGetSamplerParameteri(int sampler, int pname)
	{
		return GL33.glGetSamplerParameteri(sampler, pname);
	}

	@Override
	public int[] glGetSamplerParameterI(int sampler, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL33.glGetSamplerParameterI(sampler, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetSamplerParameterIi(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterIi(sampler, pname);
	}
	
	@Override
	public int[] glGetSamplerParameterIu(int sampler, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL33.glGetSamplerParameterIu(sampler, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetSamplerParameterIui(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterIui(sampler, pname);
	}
	
	@Override
	public String glGetStringi(int name, int index)
	{
		return GL30.glGetStringi(name, index);
	}
	
	@Override
	public int[] glGetSync(long sync, int pname, int[] length, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL32.glGetSync(sync, pname, BufferHelper.createWrapper(length), BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetSynci(long sync, int pname)
	{
		IntBuffer ret = BufferHelper.createIntBuffer(1);
		
		GL32.glGetSynci(sync, pname, ret);
		
		return ret.get();
	}
	
	@Override
	public int[] glGetTexParameterI(int target, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGetTexParameterI(target, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int glGetTexParameterIi(int target, int pname)
	{
		return GL30.glGetTexParameterIi(target, pname);
	}
	
	@Override
	public int[] glGetTexParameterIu(int target, int pname, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGetTexParameterIu(target, pname, BufferHelper.createWrapper(ret));
		
		return ret;
	}

	@Override
	public int glGetTexParameterIui(int target, int pname)
	{
		return GL30.glGetTexParameterIui(target, pname);
	}
	
	@Override
	public String glGetTransformFeedbackVarying(int program, int index, int bufSize, int[] size, int[] type)
	{
		return GL30.glGetTransformFeedbackVarying(program, index, bufSize, BufferHelper.createWrapper(size), BufferHelper.createWrapper(type));
	}
	
	@Override
	public int glGetUniformBlockIndex(int program, String uniformBlockName)
	{
		return GL31.glGetUniformBlockIndex(program, uniformBlockName);
	}
	
	@Override
	public int[] glGetUniformIndices(int program, String[] uniformNames)
	{
		int[] ret = new int[uniformNames.length];
		
		GL31.glGetUniformIndices(program, uniformNames, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int[] glGetUniformu(int program, int location, int argcount)
	{
		int[] ret = new int[argcount];
		
		GL30.glGetUniformu(program, location, BufferHelper.createWrapper(ret));
		
		return ret;
	}
	
	@Override
	public int[] glGetVertexAttribI(int index, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int[] glGetVertexAttribIu(int index, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean glIsEnabledi(int target, int index)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean glIsFramebuffer(int framebuffer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean glIsRenderbuffer(int renderbuffer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean glIsSampler(int sampler)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean glIsSync(long sync)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean glIsVertexArray(int array)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ByteBuffer glMapBufferRange(int target, long offset, long length,
			int access, ByteBuffer old_buffer)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void glPrimitiveRestartIndex(int index)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glProvokingVertex(int mode)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glQueryCounter(int id, int target)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glRenderbufferStorage(int target, int internalformat,
			int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glRenderbufferStorageMultisample(int target, int samples,
			int internalformat, int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glSampleMaski(int index, int mask)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] glSamplerParameterf(int sampler, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] glSamplerParameter(int sampler, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void glSamplerParameterf(int sampler, int pname, float param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glSamplerParameteri(int sampler, int pname, int param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] glSamplerParameterI(int sampler, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] glSamplerParameterIu(int sampler, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void glTexBuffer(int target, int internalformat, int buffer)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glTexImage2DMultisample(int target, int samples,
			int internalformat, int width, int height,
			boolean fixedsamplelocations)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glTexImage3DMultisample(int target, int samples,
			int internalformat, int width, int height, int depth,
			boolean fixedsamplelocations)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] glTexParameterI(int target, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void glTexParameterIi(int target, int pname, int param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] glTexParameterIu(int target, int pname, int argcount)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void glTexParameterIui(int target, int pname, int param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glTransformFeedbackVaryings(int program, String[] varyings,
			int bufferMode)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniformBlockBinding(int program, int uniformBlockIndex,
			int uniformBlockBinding)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribDivisor(int index, int divisor)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI1(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI1i(int index, int x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI1u(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI1ui(int index, int x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI2(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI2i(int index, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI2u(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI2ui(int index, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI3(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI3i(int index, int x, int y, int z)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI3u(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI3ui(int index, int x, int y, int z)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI4(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI4i(int index, int x, int y, int z, int w)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI4u(int index, int[] v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribI4ui(int index, int x, int y, int z, int w)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribIPointer(int index, int size, int type,
			int stride, int[] buffer)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribIPointer(int index, int size, int type,
			int stride, long buffer_buffer_offset)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP1u(int index, int type, boolean normalized,
			int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP1ui(int index, int type, boolean normalized,
			int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP2u(int index, int type, boolean normalized,
			int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP2ui(int index, int type, boolean normalized,
			int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP3u(int index, int type, boolean normalized,
			int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP3ui(int index, int type, boolean normalized,
			int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP4u(int index, int type, boolean normalized,
			int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttribP4ui(int index, int type, boolean normalized,
			int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexP2u(int type, int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexP2ui(int type, int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexP3u(int type, int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexP3ui(int type, int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexP4u(int type, int[] value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexP4ui(int type, int value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glWaitSync(long sync, int flags, long timeout)
	{
		// TODO Auto-generated method stub
		
	}
	
}
